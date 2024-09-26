package ${template.servicePackageName};

import com.u8d75.core.base.service.impl.BaseService;
import ${template.daoPackageName}.${template.className}Dao;
import ${template.domainPackageName}.${template.className};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${template.remarks}
 * ${template.author!}
 */
@Service
public class ${template.className}ServiceImpl extends BaseService<${template.className}> {

    @Autowired
    private ${template.className}Dao ${template.className?uncap_first}Dao;

    @SuppressWarnings("unchecked")
    @Override
    public ${template.className}Dao getDao() {
        return ${template.className?uncap_first}Dao;
    }
}
