package com.simpletour.gateway.ctrip.rest.ws;

import com.simpletour.common.restful.service.BaseRESTfulService;
import com.simpletour.common.utils.MD5;
import com.simpletour.gateway.ctrip.config.SysConfig;
import com.simpletour.gateway.ctrip.rest.pojo.VerifyOrderRequest;
import com.simpletour.gateway.ctrip.rest.pojo.VerifyResponse;
import com.simpletour.gateway.ctrip.rest.pojo.VerifyTransRequest;
import com.simpletour.gateway.ctrip.rest.pojo.type.RequestHeaderType;
import com.simpletour.gateway.ctrip.rest.pojo.type.orderType.ExtendInfoType;
import com.simpletour.gateway.ctrip.rest.pojo.type.orderType.PassengerInfo;
import com.simpletour.gateway.ctrip.rest.pojo.type.orderType.RequestBodyType;
import com.simpletour.gateway.ctrip.rest.pojo.type.transType.RequestBodyTypeForTrans;
import com.simpletour.gateway.ctrip.rest.service.CtripValidator;
import com.simpletour.gateway.ctrip.util.StringUtils;
import com.simpletour.gateway.ctrip.util.XMLParseUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mario on 16/01/15.
 */
@Path("test")
@Consumes({MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_XML})
@Component
public class CtripResourceTest extends BaseRESTfulService {

    @Resource
    private CtripValidator ctripValidator;

    @Value("${xiecheng.signkey}")
    private String signKey;

    /**
     * 针对验证订单和下单接口构造数据
     *
     * @return
     */
    private String buildString(String serviceName) throws UnsupportedEncodingException {
        //构造数据
        //1.构造body信息
        RequestBodyType bodyType = new RequestBodyType();
        ExtendInfoType extendInfoType = new ExtendInfoType();
        extendInfoType.setProductType("0");
        bodyType.setExtendInfo(extendInfoType);
        bodyType.setProductId("470");
        bodyType.setPrice("130");
        bodyType.setCount(2);
        bodyType.setContactName("偏分偏出三分");
        bodyType.setContactMobile("130111111111");
        bodyType.setUseDate("2016-02-15");

        List<PassengerInfo> passengerInfos = new ArrayList<>();
        PassengerInfo passengerInfo = new PassengerInfo();
        passengerInfo.setName("马大叔");
        passengerInfo.setMobile("909090909090");
        passengerInfo.setCardType("1");
        passengerInfo.setCardNo("511102199107200011");
        passengerInfos.add(passengerInfo);

        PassengerInfo passengerInfo1 = new PassengerInfo();
        passengerInfo1.setName("马大叔他大叔");
        passengerInfo1.setMobile("010101010101010");
        passengerInfo1.setCardType("1");
        passengerInfo1.setCardNo("511102199107200011");
        passengerInfos.add(passengerInfo1);
        bodyType.setPassengerInfos(passengerInfos);


        //2.构造header信息
        RequestHeaderType headerType = new RequestHeaderType();
        headerType.setAccountId("71");
        headerType.setServiceName(serviceName);
        headerType.setRequestTime("2016-2-15 11:16:31");
        headerType.setVersion("2.0");

        //组装最后的数据
        VerifyOrderRequest request = new VerifyOrderRequest();
        request.setHeader(headerType);
        request.setBody(bodyType);

        String xml = XMLParseUtil.convertToXml(request);
        String xmlBodyString = StringUtils.formatXml(XMLParseUtil.subBodyStringForXml(xml));

        //3.编码sign
        String xmlBase64 = org.bouncycastle.util.encoders.Base64.toBase64String(xmlBodyString.getBytes("UTF-8"));
        StringBuffer buffer = new StringBuffer();
        buffer.append(headerType.getAccountId());
        buffer.append(headerType.getServiceName());
        buffer.append(headerType.getRequestTime());
        buffer.append(xmlBase64);
        buffer.append(headerType.getVersion());
        buffer.append(signKey);
        String sign = MD5.getMD5String(buffer.toString().getBytes()).toLowerCase();
        headerType.setSign(sign);

        return XMLParseUtil.convertToXml(request);
    }

    private String buildStringForCancelOrder(String serviceName) throws UnsupportedEncodingException {
        //构造数据
        //1.构造body信息
        RequestBodyType bodyType = new RequestBodyType();
        bodyType.setOtaOrderId("zxqfafjiqlfaqp9");
        bodyType.setVendorOrderId("33681016832");

        //2.构造header信息
        RequestHeaderType headerType = new RequestHeaderType();
        headerType.setAccountId("71");
        headerType.setServiceName(serviceName);
        headerType.setRequestTime("2015-10-19 16:05:31");
        headerType.setVersion("2.0");

        //组装最后的数据
        VerifyOrderRequest request = new VerifyOrderRequest();
        request.setHeader(headerType);
        request.setBody(bodyType);

        String xml = XMLParseUtil.convertToXml(request);
        String xmlBodyString = StringUtils.formatXml(XMLParseUtil.subBodyStringForXml(xml));

        //3.编码sign
        String xmlBase64 = org.bouncycastle.util.encoders.Base64.toBase64String(xmlBodyString.getBytes("UTF-8"));
        StringBuffer buffer = new StringBuffer();
        buffer.append(headerType.getAccountId());
        buffer.append(headerType.getServiceName());
        buffer.append(headerType.getRequestTime());
        buffer.append(xmlBase64);
        buffer.append(headerType.getVersion());
        buffer.append(signKey);
        String sign = MD5.getMD5String(buffer.toString().getBytes()).toLowerCase();
        headerType.setSign(sign);

        return XMLParseUtil.convertToXml(request);
    }

    private String buildStringForQueryOrder(String serviceName) throws UnsupportedEncodingException {
        //构造数据
        //1.构造body信息
        RequestBodyType bodyType = new RequestBodyType();
        bodyType.setOtaOrderId("zxqfafjiqlfaqp9");
        bodyType.setVendorOrderId("33681016832");

        //2.构造header信息
        RequestHeaderType headerType = new RequestHeaderType();
        headerType.setAccountId("71");
        headerType.setServiceName(serviceName);
        headerType.setRequestTime("2015-10-19 16:05:31");
        headerType.setVersion("2.0");

        //组装最后的数据
        VerifyOrderRequest request = new VerifyOrderRequest();
        request.setHeader(headerType);
        request.setBody(bodyType);

        String xml = XMLParseUtil.convertToXml(request);
        String xmlBodyString = StringUtils.formatXml(XMLParseUtil.subBodyStringForXml(xml));

        //3.编码sign
        String xmlBase64 = org.bouncycastle.util.encoders.Base64.toBase64String(xmlBodyString.getBytes("UTF-8"));
        StringBuffer buffer = new StringBuffer();
        buffer.append(headerType.getAccountId());
        buffer.append(headerType.getServiceName());
        buffer.append(headerType.getRequestTime());
        buffer.append(xmlBase64);
        buffer.append(headerType.getVersion());
        buffer.append(signKey);
        String sign = MD5.getMD5String(buffer.toString().getBytes()).toLowerCase();
        headerType.setSign(sign);

        return XMLParseUtil.convertToXml(request);
    }

    /**
     * 对车次构造数据
     *
     * @return
     */
    private String buildStringForTourism(String serviceName) throws UnsupportedEncodingException {
        //构造数据
        //1.构造body信息
        RequestBodyTypeForTrans bodyType = new RequestBodyTypeForTrans();
        bodyType.setDepart("成都");
//        bodyType.setArrive("CD");

        bodyType.setDate(new Date());

        //2.构造header信息
        RequestHeaderType headerType = new RequestHeaderType();
        headerType.setAccountId(SysConfig.XIECHENG_CP_SOURCE_ID + "");
        headerType.setServiceName(serviceName);
        headerType.setRequestTime("2015-10-19 16:05:31");
        headerType.setVersion("2.0");

        //组装最后的数据
        VerifyTransRequest request = new VerifyTransRequest();
        request.setHeader(headerType);
        request.setBody(bodyType);

        String xml = XMLParseUtil.convertToXml(request);
        String xmlBodyString = StringUtils.formatXml(XMLParseUtil.subBodyStringForXml(xml));

        //3.编码sign
        String xmlBase64 = org.bouncycastle.util.encoders.Base64.toBase64String(xmlBodyString.getBytes("UTF-8"));
        StringBuffer buffer = new StringBuffer();
        buffer.append(headerType.getAccountId());
        buffer.append(headerType.getServiceName());
        buffer.append(headerType.getRequestTime());
        buffer.append(xmlBase64);
        buffer.append(headerType.getVersion());
        buffer.append(signKey);
        String sign = MD5.getMD5String(buffer.toString().getBytes()).toLowerCase();
        headerType.setSign(sign);

        return XMLParseUtil.convertToXml(request);
    }

    /**
     * 下单验证接口
     *
     * @return
     */
    @POST
    @Path(SysConfig.VERIFY_ORDER_METHOD)
    public VerifyResponse verifyOrder() throws UnsupportedEncodingException, ParseException {
        return ctripValidator.validatePre(this.buildString(SysConfig.VERIFY_ORDER_METHOD), SysConfig.ORDER_HANDLER);
    }

    /**
     * 创建订单接口
     *
     * @return
     */
    @POST
    @Path(SysConfig.CREATE_ORDER_METHOD)
    public VerifyResponse createOrder() throws UnsupportedEncodingException, ParseException {
        return ctripValidator.validatePre(this.buildString(SysConfig.CREATE_ORDER_METHOD), SysConfig.ORDER_HANDLER);
    }


    /**
     * 订单取消接口
     *
     * @return
     */
    @POST
    @Path(SysConfig.CANCEL_ORDER_METHOD)
    public VerifyResponse cancelOrder() throws UnsupportedEncodingException, ParseException {
        return ctripValidator.validatePre(this.buildStringForCancelOrder(SysConfig.CANCEL_ORDER_METHOD), SysConfig.ORDER_HANDLER);
    }

    /**
     * 订单查询接口
     *
     * @return
     */
    @POST
    @Path(SysConfig.QUERY_ORDER_METHOD)
    public VerifyResponse queryOrder() throws UnsupportedEncodingException, ParseException {
        return ctripValidator.validatePre(this.buildStringForQueryOrder(SysConfig.QUERY_ORDER_METHOD), SysConfig.ORDER_HANDLER);
    }

    /**
     * 凭证重发接口
     *
     * @return
     */
    @POST
    @Path(SysConfig.RESEND_METHOD)
    public VerifyResponse resend() throws UnsupportedEncodingException, ParseException {
        return ctripValidator.validatePre(this.buildStringForQueryOrder(SysConfig.RESEND_METHOD), SysConfig.ORDER_HANDLER);
    }

    /**
     * 查询行程接口
     *
     * @return
     */
    @POST
    @Path(SysConfig.QUERY_TOURISM_METHOD)
    public VerifyResponse tourismHandler() throws UnsupportedEncodingException, ParseException {
        return ctripValidator.validatePre(this.buildStringForTourism(SysConfig.QUERY_TOURISM_METHOD), SysConfig.TOURISM_HANDLER);
    }

}
