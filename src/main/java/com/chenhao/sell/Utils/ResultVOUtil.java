package com.chenhao.sell.Utils;

import com.chenhao.sell.vo.ResultVO;

public class ResultVOUtil
{
    public static ResultVO success(Object object)
    {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success()
    {
        return success(null);
    }

    public static ResultVO error()
    {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg("失败");
        return resultVO;
    }
}
