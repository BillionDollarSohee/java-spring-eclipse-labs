package kr.or.kosa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kr.or.kosa.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
