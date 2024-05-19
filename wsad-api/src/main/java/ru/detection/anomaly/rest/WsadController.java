package ru.detection.anomaly.rest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.detection.anomaly.model.entity.SessionRequest;
import ru.detection.anomaly.rest.service.RequestProcessingService;

import javax.validation.Valid;

@Controller
@RequestMapping("wsad")
public class WsadController {

    private RequestProcessingService service;

    @PostMapping(path = "/request",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void newRequest(@Valid @RequestBody SessionRequest request) {
        service.processRequest(request);
    }
}