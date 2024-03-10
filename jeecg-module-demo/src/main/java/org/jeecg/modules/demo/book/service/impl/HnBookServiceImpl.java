package org.jeecg.modules.demo.book.service.impl;

import org.jeecg.modules.demo.book.entity.HnBook;
import org.jeecg.modules.demo.book.mapper.HnBookMapper;
import org.jeecg.modules.demo.book.service.IHnBookService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 书籍
 * @Author: jeecg-boot
 * @Date:   2024-03-09
 * @Version: V1.0
 */
@Service
public class HnBookServiceImpl extends ServiceImpl<HnBookMapper, HnBook> implements IHnBookService {

}
