package com.haynespro.assessment.mmt.helper;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = {"integration"})
@SpringBootTest
public abstract class ITBase {}
