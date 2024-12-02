package com.koreatech.market.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.koreatech.market.controller.dto.request.MemberJoinRequest;
import com.koreatech.market.controller.dto.request.MemberLoginRequest;
import com.koreatech.market.controller.dto.request.MemberModifyRequest;
import com.koreatech.market.controller.dto.response.MemberInfoResponse;
import com.koreatech.market.controller.dto.response.MemberJoinResponse;
import com.koreatech.market.service.MemberService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberJoinResponse> join(@RequestBody @Valid MemberJoinRequest request) {
        MemberJoinResponse response = memberService.join(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid MemberLoginRequest request, HttpSession session) {
        Long memberId = memberService.login(request.email(), request.password());
        session.setAttribute("memberId", memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<MemberInfoResponse> getInfo(@SessionAttribute(name = "memberId") Long userId) {
        MemberInfoResponse response = memberService.getInfo(userId);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<MemberInfoResponse> modifyInfo(
        @SessionAttribute(name = "memberId") Long userId,
        @RequestBody @Valid MemberModifyRequest request
    ) {
        memberService.modifyInfo(userId, request);
        return ResponseEntity.ok().build();
    }
}
