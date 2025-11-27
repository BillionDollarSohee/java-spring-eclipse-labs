package kr.or.kosa.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.stereotype.Service;

@Service
public class GitToolService {

    /**
     * MCP Tool : 로컬 Git 저장소의 브랜치 목록 조회
     *
     * args:
     *   repoPath : Git 저장소 루트 경로 (예: "C:/workspace/my-project")
     */
    @McpTool(
        name = "listBranches",
        description = "주어진 Git 저장소 경로의 로컬 브랜치 목록을 반환합니다."
    )
    public List<String> listBranches(Map<String, Object> args) throws Exception {

        String repoPath = (String) args.getOrDefault("repoPath", ".");
        // repoPath/.git 기준으로 저장소 찾기
        Path gitDir = Paths.get(repoPath, ".git");

        var builder = new FileRepositoryBuilder()
                .setGitDir(gitDir.toFile())
                .readEnvironment()
                .findGitDir();

        try (var repository = builder.build();
             var git = new Git(repository)) {

            List<Ref> branches = git.branchList().call();

            return branches.stream()
                    .map(ref -> ref.getName().replace("refs/heads/", ""))
                    .toList();
        }
    }

    /**
     * MCP Tool : Git 저장소의 현재 상태 조회
     *
     * args:
     *   repoPath : Git 저장소 루트 경로
     */
    @McpTool(
        name = "showStatus",
        description = "Git 저장소의 현재 상태(변경된 파일 등)를 반환합니다."
    )
    public String showStatus(Map<String, Object> args) throws Exception {

        String repoPath = (String) args.getOrDefault("repoPath", ".");
        Path gitDir = Paths.get(repoPath, ".git");

        var builder = new FileRepositoryBuilder()
                .setGitDir(gitDir.toFile())
                .readEnvironment()
                .findGitDir();

        try (var repository = builder.build();
             var git = new Git(repository)) {

            var status = git.status().call();

            return String.format(
                "Modified: %s, Added: %s, Removed: %s, Untracked: %s",
                status.getModified(),
                status.getAdded(),
                status.getRemoved(),
                status.getUntracked()
            );
        }
    }

    /**
     * MCP Tool : 최신 커밋 메시지 조회
     *
     * args:
     *   repoPath : Git 저장소 루트 경로
     */
    @McpTool(
        name = "getLatestCommit",
        description = "Git 저장소의 최신 커밋 메시지를 반환합니다."
    )
    public String getLatestCommit(Map<String, Object> args) throws Exception {

        String repoPath = (String) args.getOrDefault("repoPath", ".");
        Path gitDir = Paths.get(repoPath, ".git");

        var builder = new FileRepositoryBuilder()
                .setGitDir(gitDir.toFile())
                .readEnvironment()
                .findGitDir();

        try (var repository = builder.build();
             var git = new Git(repository)) {

            Iterable<RevCommit> commits = git.log().setMaxCount(1).call();
            RevCommit latestCommit = commits.iterator().next();

            return String.format(
                "Commit: %s\nAuthor: %s\nDate: %s\nMessage: %s",
                latestCommit.getName(),
                latestCommit.getAuthorIdent().getName(),
                latestCommit.getAuthorIdent().getWhen(),
                latestCommit.getFullMessage()
            );
        }
    }
}