package com.spring.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.springboot.entity.Card;
import com.spring.springboot.mapper.CardMapper;
import org.springframework.stereotype.Service;

@Service
public class CardService extends ServiceImpl<CardMapper, Card> {


}
