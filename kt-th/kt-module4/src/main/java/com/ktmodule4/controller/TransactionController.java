package com.ktmodule4.controller;

import com.ktmodule4.model.Transaction;
import com.ktmodule4.service.CustomerService;
import com.ktmodule4.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerService customerService;

    // 1. Danh sách giao dịch
    @GetMapping
    public String listTransaction(@RequestParam(required = false) String ten,
                                  @RequestParam(required = false) String loai,
                                  Model model) {

        List<Transaction> transaction = (ten != null || loai != null)
                ? transactionService.search(ten != null ? ten : "", loai != null ? loai : "")
                : transactionService.findAll();

        model.addAttribute("listTransaction", transaction);
        return "transaction/list";
    }


    // GET
    @GetMapping("/create")
    public String showFormThemMoi(Model model) {
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("customerList", customerService.findAll());
        model.addAttribute("typeList", List.of("Đất", "Nhà và đất"));
        return "transaction/create";
    }

    // POST
    @PostMapping("/create")
    public String saveGiaoDich(@Valid @ModelAttribute("transaction") Transaction transaction,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("customerList", customerService.findAll());
            model.addAttribute("typeList", List.of("Đất", "Nhà và đất"));
            return "transaction/create";
        }
        transactionService.save(transaction);
        return "redirect:/transaction";
    }

    // 3. Chi tiết và xóa giao dịch
    @GetMapping("/detail/{id}")
    public String detailTransaction(@PathVariable Long id, Model model) {
        Transaction gd = transactionService.findById(id).orElseThrow();
        model.addAttribute("transaction", gd);
        return "transaction/detail";
    }

    @PostMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        transactionService.deleteById(id);
        return "redirect:/transaction";
    }
}
