package com.mailconfigmanager.service;

import com.mailconfigmanager.model.MailConfig;

import java.util.List;

public interface IMailConfigService {
    List<MailConfig> findAll();
    void save(MailConfig mailConfig);
    MailConfig findById(int id);
    void update(int id, MailConfig mailConfig);
    void remove(int id);
}
