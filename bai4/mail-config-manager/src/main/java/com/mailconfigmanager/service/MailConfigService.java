package com.mailconfigmanager.service;

import com.mailconfigmanager.model.MailConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MailConfigService implements IMailConfigService {

    private static final Map<Integer, MailConfig> mailConfig;

    static {
        mailConfig = new HashMap<>();
        mailConfig.put(1, new MailConfig(1, "English", 50, true, "Nguyen Hoang\nThuan Thanh Bac Ninh"));
        mailConfig.put(2, new MailConfig(2, "Vietnamese", 25, false, "Dang Luan\nHang Cot Ha Noi"));
        mailConfig.put(3, new MailConfig(3, "Japanese", 100, false, "Uchiha Madara\nShibuya Tokyo"));
        mailConfig.put(4, new MailConfig(4, "Chinese", 10, true, "Xiao BinLin\nHú nán"));
    }

    @Override
    public List<MailConfig> findAll() {
        return new ArrayList<>(mailConfig.values());
    }

    @Override
    public void save(MailConfig config) {
        mailConfig.put(config.getId(), config);
    }

    @Override
    public MailConfig findById(int id) {
        return mailConfig.get(id);
    }

    @Override
    public void update(int id, MailConfig newConfig) {
        mailConfig.put(id, newConfig);
    }

    @Override
    public void remove(int id) {
        mailConfig.remove(id);
    }
}
