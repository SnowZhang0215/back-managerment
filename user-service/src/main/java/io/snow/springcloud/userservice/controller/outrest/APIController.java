package io.snow.springcloud.userservice.controller.outrest;


import com.sun.jnlp.ApiDialog;
import io.snow.model.vo.ApiVo;
import io.snow.rest.common.ResponseData;
import io.snow.rest.common.page.PageResult;
import io.snow.springcloud.userservice.service.IAPIEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiEntity")
public class APIController {

    private static final Logger logger = LoggerFactory.getLogger(APIController.class);

    @Autowired
    private IAPIEntityService apiEntityService;

    @PostMapping("/edit")
    public ResponseData editApiEntity(@RequestBody ApiVo apiVo,@RequestHeader("userName") String userName){
        try {
            apiVo.setLastModifiedBy(userName);
            apiVo.setLastModifiedDate(Instant.now());

            int row = apiEntityService.editApiEntity(apiVo);
            if (row > 0){
                return ResponseData.ok(row);
            }else {
                return ResponseData.error("更新API失败");
            }
        } catch (Exception e) {
            logger.error("edit api_entity error:{0}",e);
            return ResponseData.error(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseData createApiEntity(@RequestBody ApiVo apiVo, @RequestHeader("userName") String userName){
        try {
            apiVo.setCreatedBy(userName);
            apiVo.setCreatedDate(Instant.now());
            if (StringUtils.isEmpty(apiVo.getPath())){
                return ResponseData.error("参数错误，path必输");
            }
            ApiVo apiVoByPath = apiEntityService.getApiVoByPath(apiVo.getPath());
            if (apiVoByPath!=null){
                return ResponseData.error("已经存在path =" + apiVo.getPath() + ";的Api");
            }
            int row = apiEntityService.createApiEntity(apiVo);
            if (row > 0){
                return ResponseData.ok(row);
            }else {
                return ResponseData.error("创建API失败");
            }
        } catch (Exception e) {
            logger.error("create api_entity error:{0}",e);
            return ResponseData.error(e.getMessage());
        }
    }
    @GetMapping("/query")
    public ResponseData queryApi(@RequestParam("desc") String desc){
        try {
            List<ApiVo> result =apiEntityService.queryApiByDesc(desc);
            return ResponseData.ok(result);
        } catch (Exception e) {
            logger.error("query:{0}",e);
            return ResponseData.error("查询失败");
        }
    }

    @GetMapping("/list")
    public ResponseData listApi(@RequestParam("pageNum") Integer pageIndex,@RequestParam("pageSize") Integer pageSize){
        try {
            PageResult result = apiEntityService.listApiEntity(pageIndex, pageSize);
            return ResponseData.ok(result);
        } catch (Exception e) {
            logger.error("query:{0}",e);
            return ResponseData.error("查询失败");
        }
    }
}
