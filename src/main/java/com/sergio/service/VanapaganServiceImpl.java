package com.sergio.service;

import com.sergio.dto.VanapaganResponse;
import com.sergio.restclient.VanapaganClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VanapaganServiceImpl implements VanapaganService {

    private VanapaganClient vanapaganClient;

    @Autowired
    public VanapaganServiceImpl(VanapaganClient vanapaganClient) {
        this.vanapaganClient = vanapaganClient;
    }

    @Override
    public VanapaganResponse getAbout() {
        return vanapaganClient.getAbout();
    }
}
