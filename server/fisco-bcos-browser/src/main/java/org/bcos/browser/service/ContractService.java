package org.bcos.browser.service;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BasePageResponse;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.entity.dto.Contract;
import org.bcos.browser.entity.req.ReqContracts;
import org.bcos.browser.mapper.ContractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import static org.bcos.browser.util.CommonUtils.readZipFile;

@Slf4j
@Service
public class ContractService {
    @Autowired
    ContractMapper contractMapper;

    /**
     * addContract.
     * 
     * @param contracts info
     * @return
     */
    public BaseResponse addContract(ReqContracts contracts) throws BaseException {
        log.info("addContract contracts:{}", contracts);
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        List<String> addFailContractName = new ArrayList<>();
        for (Contract loop : contracts.getData()) {
            int count = contractMapper.getContractByNameAndPath(loop.getContractName(),loop.getContractPath());
            if (count > 0) {
                addFailContractName.add(loop.getContractName());
            }else {
                contractMapper.add(loop);
            }
        }
        if(addFailContractName.size() != 0){
            response.setData(addFailContractName);
        }
        return response;
    }



    /**
     * addZipContracts.
     *
     * @param
     * @return
     */
    public BaseResponse addBatchContracts(MultipartFile zipFile, Contract contract) throws BaseException, IOException {
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        File file = new File("temp" + File.separator + zipFile.getOriginalFilename());
        if(!file.getName().endsWith(".zip")){
            throw new BaseException(ConstantCode.NOT_A_ZIP_FILE);
        }
        zipFile.transferTo(file);
        ZipFile zf = new ZipFile(file);
        for (Enumeration entries = zf.entries(); entries.hasMoreElements(); ){
            ZipEntry zipEntry = (ZipEntry) entries.nextElement();
            String zipEntryName = zipEntry.getName();
            int size = (int) zipEntry.getSize();
            if(zipEntry.isDirectory()){
                continue;
            }
            if(!zipEntryName.endsWith(".sol")){
                continue;
            }
            String contractPath = null;
            String contractName = null;
            if (zipEntryName.contains(File.separator)){
                String[] strings = zipEntryName.split(File.separator);
                if(strings.length>4){
                    continue;
                }
                if (strings[strings.length-1].startsWith(".")){
                    continue;
                }
                contractPath = File.separator + zipEntryName.substring(0,zipEntryName.lastIndexOf(File.separator)+1);
                contractName = zipEntryName.substring(zipEntryName.lastIndexOf(File.separator)+1, zipEntryName.length()-4);
            }else {
                contractName = zipEntryName.substring(0,zipEntryName.lastIndexOf("."));
                contractPath = File.separator;
            }
            String contractSource = readZipFile(zipEntry, zf);
            String crypontractSource = Base64.getEncoder().encodeToString(contractSource.getBytes("UTF-8"));
            contract.setContractName(contractName);
            contract.setContractPath(contractPath);
            contract.setContractSource(crypontractSource);
            contractMapper.add(contract);
        }
        zf.close();
        if(file.exists()){
            file.delete();
        }
        return response;
    }


    /**
     * getContractList.
     * 
     * @return
     */
    public BasePageResponse getContractList(int pageNumber, int pageSize) {
        BasePageResponse response = new BasePageResponse(ConstantCode.SUCCESS);
        int start =
                Optional.ofNullable(pageNumber).map(page -> (page - 1) * pageSize).orElse(null);
        int total = contractMapper.getContractCnts();
        List<Contract> list = contractMapper.getContractList(start, pageSize);
        response.setTotalCount(total);
        response.setData(list);
        return response;
    }

    /**
     * updateContractList.
     * 
     * @param contracts info
     * @return
     */
    public BaseResponse updateContract(ReqContracts contracts) {
        log.info("updateContract contracts:{}", contracts);
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        for (Contract loop : contracts.getData()) {
            if (StringUtils.isBlank(loop.getContractAbi())) {
                loop.setContractStatus(2);
            } else {
                loop.setContractStatus(1);
            }
            contractMapper.updateContract(loop);
        }
        return response;
    }

    /**
     * deleteContract.
     * @param contractNOs contractNOs
     * @return
     */
    public BaseResponse deleteContract(String contractNOs) {
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        String[] contractNoArr = contractNOs.split(",");
        for(int i = 0;i<contractNoArr.length;i++){
            int contractId = Integer.parseInt(contractNoArr[i]);
            contractMapper.deleteContract(contractId);
        }
        return response;
    }

}
