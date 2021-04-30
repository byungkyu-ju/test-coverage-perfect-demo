package me.bk.testcoverageperfectdemo.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.bk.testcoverageperfectdemo.member.domain.Member;

/**
 * @author : byungkyu
 * @date : 2021/04/29
 * @description :
 **/
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);
}
