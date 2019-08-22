package org.bcos.browser.service;

import static org.bcos.browser.util.CommonUtils.preTreatmentFile;
import static org.bcos.browser.util.CommonUtils.readZipFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.lang3.StringUtils;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.exception.BaseException;
import org.bcos.browser.entity.base.BasePageResponse;
import org.bcos.browser.entity.base.BaseResponse;
import org.bcos.browser.entity.dto.Contract;
import org.bcos.browser.entity.dto.Function;
import org.bcos.browser.entity.req.ReqContracts;
import org.bcos.browser.entity.req.ReqFunction;
import org.bcos.browser.mapper.ContractMapper;
import org.bcos.browser.mapper.FunctionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContractService {
    @Autowired
    ContractMapper contractMapper;
    @Autowired
    FunctionMapper functionMapper;

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
        preTreatmentFile(zf);  //check file format right or not,if not ,throw excepetion
        for (Enumeration entries = zf.entries(); entries.hasMoreElements(); ){
            ZipEntry zipEntry = (ZipEntry) entries.nextElement();
            String zipEntryName = zipEntry.getName();
            if (zipEntryName.startsWith("__MACOSX")){  //only in macos
                continue;
            }
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
                if(strings.length>2){
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
            int count = contractMapper.getContractByNameAndPath(contractName,contractPath);
            if (count > 0) {
                throw new BaseException(ConstantCode.CONTRACT_ALREADY_EXIST);
            }else {
                contractMapper.add(contract);
            }
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
     * updateContract.
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
     * addFunction.
     * 
     * @param functions info
     * @return
     */
    @Transactional
    public BaseResponse addFunction(ReqFunction functions) {
        log.info("addFunction functions:{}", functions);
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        for (Function loop : functions.getData()) {
            try {
                functionMapper.add(loop);
            } catch (DuplicateKeyException e) {
                log.warn("addFunction methodId:{} is existed", loop.getMethodId());
                continue;
            }
        }
        return response;
    }
    
    /**
     * getFunction.
     * 
     * @param methodId methodId
     * @return
     */
    public BaseResponse getFunction(String methodId) {
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        Function function = functionMapper.getFunctionById(methodId);
        response.setData(function);
        return response;
    }
    
    /**
     * getContractAbi.
     * 
     * @param input info
     * @return
     */
    public BaseResponse getContractAbi(String input) {
        BaseResponse response = new BaseResponse(ConstantCode.SUCCESS);
        Contract contractAbi = contractMapper.getAbiByInput(input);
        response.setData(contractAbi);
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
