package org.bcos.browser.bcos.browser.controller;

import java.sql.Timestamp;
import java.util.List;

import org.bcos.browser.bcos.browser.dao.StatServiceDAO;
import org.bcos.browser.bcos.browser.dto.SingleStatInfoDTO;
import org.bcos.browser.bcos.browser.dto.StatBlockInfoDTO;
import org.bcos.browser.bcos.browser.dto.StatTransactionInfoDTO;
import org.bcos.browser.bcos.browser.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping
public class BrowserController {
	private static Logger logger = LoggerFactory.getLogger(BrowserController.class);
	
	@Autowired
	private StatServiceDAO statServiceDAO;
	@Autowired
	private SingleStatInfoDTO singleStatInfo;
	@Autowired
	private StatBlockInfoDTO statBlockInfoDTO;
	@Autowired
	private StatTransactionInfoDTO statTransactionInfoDTO;
	
	/**
	 * receive post josn data
	 * @param object
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/browserFacade", method = RequestMethod.POST)
	public synchronized String browserFacade(@Validated @RequestBody Object object, BindingResult result) {
		logger.info("browserFacade object:{}", JSON.toJSONString(object));
		JSONObject json = JSONObject.parseObject(JSON.toJSONString(object));
		logger.debug("###object：{}###", json);
		JSONArray metricDataList = json.getJSONArray("metricDataList");
		long jsonSize = metricDataList.size();
		for (int j = 0; j < jsonSize; j++) {
			JSONObject metricData = metricDataList.getJSONObject(j);
			String attr=metricData.getString("attr");
			if("tx_flow".equals(attr)){
				JSONArray metricValue = metricData.getJSONArray("metricValue");
				logger.debug("###metricValue：{}###", metricValue);
				long metricValuejsonArrjsonSize = metricValue.size();
				for(int i=0;i<metricValuejsonArrjsonSize;i++){
					JSONObject jsonMetricValue = metricValue.getJSONObject(i);
					logger.debug("###jsonTrans：{}###", jsonMetricValue);
					statTransactionInfoDTO.setHash(jsonMetricValue.getString("hash"));
					statBlockInfoDTO.setCollectTimestamp(new Timestamp(Long.parseLong(metricData.getString("collectTimestamp"))));
					statTransactionInfoDTO.setStartMsg(null != jsonMetricValue.getJSONObject("start").getString("msg")?jsonMetricValue.getJSONObject("start").getString("msg"):"");
					statTransactionInfoDTO.setStartTime(jsonMetricValue.getJSONObject("start").getString("time"));
					statTransactionInfoDTO.setOnChainMsg(null != jsonMetricValue.getJSONObject("onChain").getString("msg")?jsonMetricValue.getJSONObject("onChain").getString("msg"):"");
					statTransactionInfoDTO.setOnChainTime(jsonMetricValue.getJSONObject("onChain").getString("time"));
					statTransactionInfoDTO.setDetailInfo(json.toString());
					statTransactionInfoDTO.setObject(metricData.getString("object"));
					String tableName="tb_stat_transaction_"+DateUtil.NowDateInfo();
					Boolean flag=true;
					List<StatTransactionInfoDTO> transactionList=statServiceDAO.selectAllStatTransaction(tableName);
					for(int a=0;a<transactionList.size();a++){
						String TransactionObject=statTransactionInfoDTO.getObject();
						String TransactionHash=statTransactionInfoDTO.getHash();
						if(TransactionObject.equals(transactionList.get(a).getObject())&&TransactionHash.equals(transactionList.get(a).getHash())){
							flag=false;
							break;
						}
					}
					if (flag){
						statServiceDAO.insertStatTransactionInfo(statTransactionInfoDTO);
					}
			     }
		    }else if("block_flow".equals(attr))	{
				JSONArray metricValue = metricData.getJSONArray("metricValue");
				logger.debug("###metricValue：{}###", metricValue);
		    	for(int i=0;i<metricValue.size();i++){
					JSONObject jsonMetricValue = metricValue.getJSONObject(i);
					logger.debug("###jsonTrans：{}###", jsonMetricValue);
					statBlockInfoDTO.setHash(null!=jsonMetricValue.getString("hash")?jsonMetricValue.getString("hash"):"");
					statBlockInfoDTO.setHeight(Integer.valueOf(jsonMetricValue.getString("height")));
					statBlockInfoDTO.setCollectTimestamp(new Timestamp(Long.parseLong(metricData.getString("collectTimestamp"))));
					statBlockInfoDTO.setStart(null!=jsonMetricValue.getString("start")?jsonMetricValue.getJSONObject("start").getString("time"):"");
					statBlockInfoDTO.setSealed(null!=jsonMetricValue.getString("sealed")?jsonMetricValue.getJSONObject("sealed").getString("time"):"");
					statBlockInfoDTO.setExeced(null!=jsonMetricValue.getString("execed")?jsonMetricValue.getJSONObject("execed").getString("time"):"");
					statBlockInfoDTO.setSigned(null!=jsonMetricValue.getString("signed")?jsonMetricValue.getJSONObject("signed").getString("time"):"");
					statBlockInfoDTO.setCommited(null!=jsonMetricValue.getString("commited")?jsonMetricValue.getJSONObject("commited").getString("time"):"");
					statBlockInfoDTO.setOnChain(null!=jsonMetricValue.getString("onChain")?jsonMetricValue.getJSONObject("onChain").getString("time"):"");
					statBlockInfoDTO.setViewchange_start(null!=jsonMetricValue.getString("viewchange_start")?jsonMetricValue.getJSONObject("viewchange_start").getString("time"):"");
					statBlockInfoDTO.setViewchanged(null!=jsonMetricValue.getString("viewchanged")?jsonMetricValue.getJSONObject("viewchanged").getString("time"):"");
					statBlockInfoDTO.setDetailInfo(json.toString());
					statBlockInfoDTO.setObject(metricData.getString("object"));
					if("".equals(statBlockInfoDTO.getHash())){
						statServiceDAO.insertStatBlockInfo(statBlockInfoDTO);
					}else{
						String tableName="tb_stat_block_"+DateUtil.NowDateInfo();
						Boolean flag=true;
						List<StatBlockInfoDTO> statBlockList=statServiceDAO.selectAllBlock(tableName);
						for(int a=0;a<statBlockList.size();a++){
							String blockObject=statBlockInfoDTO.getObject();
							String blockHash=statBlockInfoDTO.getHash();
							if(blockObject.equals(statBlockList.get(a).getObject())&&blockHash.equals(statBlockList.get(a).getHash())){
								flag=false;
								break;
							}
						}
						if (flag){
							statServiceDAO.insertStatBlockInfo(statBlockInfoDTO);
						}
					}
				}
		    }else{
					logger.debug("###jsonTrans：{}###", metricData);
					singleStatInfo.setObject(metricData.getString("object"));
					singleStatInfo.setAttr(metricData.getString("attr"));
					singleStatInfo.setAttrName(metricData.getString("attrName"));
					singleStatInfo.setCollectTimestamp(new Timestamp(Long.parseLong(metricData.getString("collectTimestamp"))));
					singleStatInfo.setMetricValue(Double.valueOf(metricData.getString("metricValue")));
					singleStatInfo.setHostIp(metricData.getString("hostIp"));
					singleStatInfo.setDetailInfo(json.toString());
					statServiceDAO.insertSingleStatInfo(singleStatInfo);
		    }
		
	    }
		return "{'success':0}";
     }
}