package org.jeecg.modules.demo.book.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.book.bean.BookRequestParam;
import org.jeecg.modules.demo.book.entity.HnBook;
import org.jeecg.modules.demo.book.service.IHnBookService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 书籍
 * @Author: jeecg-boot
 * @Date:   2024-03-09
 * @Version: V1.0
 */
@Api(tags="书籍")
@RestController
@RequestMapping("/book/hnBook")
@Slf4j
public class HnBookController extends JeecgController<HnBook, IHnBookService> {
	@Autowired
	private IHnBookService hnBookService;
	
	/**
	 * 分页列表查询
	 *
	 * @param param
	 *
	 * @return
	 */
	//@AutoLog(value = "书籍-分页列表查询")
	@ApiOperation(value="书籍-分页列表查询", notes="书籍-分页列表查询")
	@PostMapping(value = "/bookList")
	public Result<IPage<HnBook>> queryBookPageList(@RequestBody BookRequestParam param) {
		Map<String, String[]> map=new HashMap<>();
		HnBook hnBook=new HnBook();
		QueryWrapper<HnBook> queryWrapper = QueryGenerator.initQueryWrapper(hnBook, map);
		Page<HnBook> page = new Page<HnBook>(param.getPageNo(), param.getPageSize());
		IPage<HnBook> pageList = hnBookService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	 @ApiOperation(value="书籍-分页列表查询", notes="书籍-分页列表查询")
	 @GetMapping(value = "/list")
	 public Result<IPage<HnBook>> queryPageList(HnBook hnBook,
												@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												HttpServletRequest req) {
		 QueryWrapper<HnBook> queryWrapper = QueryGenerator.initQueryWrapper(hnBook, req.getParameterMap());
		 Page<HnBook> page = new Page<HnBook>(pageNo, pageSize);
		 IPage<HnBook> pageList = hnBookService.page(page, queryWrapper);
		 return Result.OK(pageList);
	 }
	
	/**
	 *   添加
	 *
	 * @param hnBook
	 * @return
	 */
	@AutoLog(value = "书籍-添加")
	@ApiOperation(value="书籍-添加", notes="书籍-添加")
//	@RequiresPermissions("book:hn_book:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody HnBook hnBook) {
		hnBookService.save(hnBook);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param hnBook
	 * @return
	 */
	@AutoLog(value = "书籍-编辑")
	@ApiOperation(value="书籍-编辑", notes="书籍-编辑")
//	@RequiresPermissions("book:hn_book:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody HnBook hnBook) {
		hnBookService.updateById(hnBook);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "书籍-通过id删除")
	@ApiOperation(value="书籍-通过id删除", notes="书籍-通过id删除")
	@RequiresPermissions("book:hn_book:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		hnBookService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "书籍-批量删除")
	@ApiOperation(value="书籍-批量删除", notes="书籍-批量删除")
	@RequiresPermissions("book:hn_book:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.hnBookService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "书籍-通过id查询")
	@ApiOperation(value="书籍-通过id查询", notes="书籍-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<HnBook> queryById(@RequestParam(name="id",required=true) String id) {
		HnBook hnBook = hnBookService.getById(id);
		if(hnBook==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(hnBook);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param hnBook
    */
    @RequiresPermissions("book:hn_book:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HnBook hnBook) {
        return super.exportXls(request, hnBook, HnBook.class, "书籍");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("book:hn_book:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, HnBook.class);
    }

}
