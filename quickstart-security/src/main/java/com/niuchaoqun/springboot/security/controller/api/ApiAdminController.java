package com.niuchaoqun.springboot.security.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.niuchaoqun.springboot.commons.base.BaseController;
import com.niuchaoqun.springboot.commons.rest.RestError;
import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.commons.rest.RestResult;
import com.niuchaoqun.springboot.security.dto.form.AdminAddForm;
import com.niuchaoqun.springboot.security.dto.form.AdminEditForm;
import com.niuchaoqun.springboot.security.dto.form.AdminSearchForm;
import com.niuchaoqun.springboot.security.entity.Admin;
import com.niuchaoqun.springboot.security.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "管理员管理")
@RequestMapping("/api/admin")
@RestController
@Slf4j
public class ApiAdminController extends BaseController {
    @Autowired
    private AdminService adminService;

    @ApiOperation("新增管理员")
    @PostMapping("")
    public RestResult<Admin> add(@Valid AdminAddForm addForm, BindingResult result) {
        if (result.hasErrors()) {
            return RestResponse.fail(this.resultErrors(result));
        }

        try {
            Admin data = adminService.add(addForm);

            return RestResponse.data(data);
        } catch (Exception e) {
            return RestResponse.fail(e.getLocalizedMessage());
        }
    }

    @ApiOperation("管理员详情，ID查找")
    @GetMapping("/{id}")
    public RestResult<Admin> get(@PathVariable Long id) {
        if (id > 0) {
            try {
                Admin data = adminService.get(id);

                return RestResponse.data(data);
            } catch (Exception e) {
                return RestResponse.fail(e.getLocalizedMessage());
            }
        }

        return RestResponse.fail(RestError.PARAM_ERROR);
    }

    @ApiOperation("管理员关联详情，ID查找")
    @GetMapping("/relation/{id}")
    public RestResult<Admin> getRelation(@PathVariable Long id) {
        if (id > 0) {
            try {
                Admin data = adminService.getRelationById(id);

                return RestResponse.data(data);
            } catch (Exception e) {
                return RestResponse.fail(e.getLocalizedMessage());
            }
        }

        return RestResponse.fail(RestError.PARAM_ERROR);
    }

    @ApiOperation("管理员修改")
    @PostMapping("/{id}")
    public RestResult<Admin> edit(@PathVariable Long id, @Valid AdminEditForm editForm, BindingResult result) {
        if (result.hasErrors()) {
            return RestResponse.fail(this.resultErrors(result));
        }

        if (id > 0 && !editForm.isEmpty()) {
            try {
                Admin data = adminService.edit(id, editForm);
                return RestResponse.data(data);
            } catch (Exception e) {
                return RestResponse.fail(e.getLocalizedMessage());
            }
        }

        return RestResponse.fail(RestError.PARAM_ERROR);
    }

    @ApiOperation("管理员删除")
    @DeleteMapping("/{id}")
    public RestResult remove(@PathVariable Long id) {
        if (id > 0) {
            boolean result = adminService.remove(id);
            if (result) {
                return RestResponse.success();
            }
        }

        return RestResponse.fail(RestError.PARAM_ERROR);
    }

    @ApiOperation("管理员详情，username查找")
    @GetMapping("/username/{username}")
    public RestResult<Admin> getByUsername(@PathVariable String username) {
        if (StringUtils.isNotBlank(username)) {
            try {
                Admin data = adminService.getRelationByUsername(username);

                return RestResponse.data(data);
            } catch (Exception e) {
                return RestResponse.fail(e.getLocalizedMessage());
            }
        }

        return RestResponse.fail(RestError.PARAM_ERROR);
    }

    @ApiOperation("管理员分页查询")
    @GetMapping("/search")
    public RestResult<PageInfo> search(@Valid AdminSearchForm searchForm, BindingResult result) {
        if (result.hasErrors()) {
            return RestResponse.fail(this.resultErrors(result));
        }

        log.debug(searchForm.toString());

        Integer page = searchForm.getPage();
        Integer size = searchForm.getSize();
        page = page == null ? 1 : Math.max(1, page);
        size = size == null ? pageSize : Math.max(1, size);

        PageHelper.startPage(page, size);
        List<Admin> datas = adminService.search(searchForm);
        PageInfo pages = new PageInfo(datas);

        return RestResponse.data(pages);
    }
}
