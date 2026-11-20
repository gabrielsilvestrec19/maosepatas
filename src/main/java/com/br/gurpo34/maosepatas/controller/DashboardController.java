package com.br.gurpo34.maosepatas.controller;

import com.br.gurpo34.maosepatas.dto.dashboard.DashboardResponseDTO;
import com.br.gurpo34.maosepatas.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

        private final DashboardService dashboardService;

        @GetMapping
        public ResponseEntity<DashboardResponseDTO> obterResumoPainel() {
            return ResponseEntity.ok(dashboardService.obterResumoPainel());
        }

}
