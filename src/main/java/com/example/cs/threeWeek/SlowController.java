package com.example.cs.threeWeek;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlowController {

    @GetMapping("/slow")
    @Transactional
    public String slow() throws Exception {
        // DB 커넥션을 오래 잡기 위한 의도적 딜레이
        Thread.sleep(5000); // 5초 동안 트랜잭션(=커넥션) 유지
        return "done";
    }

    @GetMapping("/fast")
    public String fast() {
        return "fast";
    }
}
