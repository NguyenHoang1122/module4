package com.customerprovincemanagement.formatter;

import com.customerprovincemanagement.model.Province;
import com.customerprovincemanagement.repository.IProvinceRepository;
import com.customerprovincemanagement.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
public class ProvinceFormatter implements Formatter<Province> {
    private final IProvinceService iProvinceService;

    @Autowired
    public ProvinceFormatter(IProvinceService iProvinceService) {
        this.iProvinceService = iProvinceService;
    }

    @Override
    public Province parse(String text, Locale locale) {
        Optional<Province> provinceOptional = iProvinceService.findById(Long.parseLong(text));
        return provinceOptional.orElse(null);
    }

    @Override
    public String print(Province object, Locale locale) {
        return "[" + object.getId() + "," + object.getName() + "]";
    }
}
