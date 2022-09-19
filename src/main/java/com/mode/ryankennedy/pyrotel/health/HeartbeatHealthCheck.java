package com.mode.ryankennedy.pyrotel.health;

import com.codahale.metrics.health.HealthCheck;

public class HeartbeatHealthCheck extends HealthCheck {
    @Override
    protected Result check() {
        return Result.healthy("thump");
    }
}
