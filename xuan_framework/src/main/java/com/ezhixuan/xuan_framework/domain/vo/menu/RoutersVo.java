package com.ezhixuan.xuan_framework.domain.vo.menu;

import com.ezhixuan.xuan_framework.domain.entity.Menu;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {

    private List<Menu> menus;
}