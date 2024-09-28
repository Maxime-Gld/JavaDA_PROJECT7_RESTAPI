package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.domain.RuleName;

public interface RuleNameService {

    void createRuleName(RuleName ruleName);

    void deleteRuleName(Integer id);

    void updateRuleName(Integer id, RuleName ruleName);

    RuleName getRuleNameById(Integer id);

    List<RuleName> getAllRuleNames();
}
