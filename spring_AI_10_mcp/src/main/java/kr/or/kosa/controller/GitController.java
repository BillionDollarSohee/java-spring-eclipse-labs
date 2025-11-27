package kr.or.kosa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.kosa.service.GitToolService;

@RestController
public class GitController {

    private final GitToolService gitToolService;

    public GitController(GitToolService gitToolService) {
        this.gitToolService = gitToolService;
    }

    // 브랜치 목록 조회
    @GetMapping("/branches")
    public List<String> getBranches(@RequestParam(value = "repoPath", defaultValue = ".") String repoPath) throws Exception {
        Map<String, Object> args = new HashMap<>();
        args.put("repoPath", repoPath);
        return gitToolService.listBranches(args);
    }

    // Git 상태 조회
    @GetMapping("/status")
    public String getStatus(@RequestParam(value = "repoPath", defaultValue = ".") String repoPath) throws Exception {
        Map<String, Object> args = new HashMap<>();
        args.put("repoPath", repoPath);
        return gitToolService.showStatus(args);
    }

    // 최신 커밋 조회
    @GetMapping("/commit")
    public String getCommit(@RequestParam(value = "repoPath", defaultValue = ".") String repoPath) throws Exception {
        Map<String, Object> args = new HashMap<>();
        args.put("repoPath", repoPath);
        return gitToolService.getLatestCommit(args);
    }

    // 서버 동작 확인용
    @GetMapping("/test")
    public String test() {
        return "Server is running!";
    }
}