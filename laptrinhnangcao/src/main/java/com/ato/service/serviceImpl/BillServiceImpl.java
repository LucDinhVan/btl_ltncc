package com.ato.service.serviceImpl;

import com.ato.model.baseModel.DataListDTO;
import com.ato.dao.BillDAO;
import com.ato.model.bo.Bill;
import com.ato.model.dto.BillDTO;
import com.ato.service.IBillService;
import com.ato.utils.Translator;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Log4j2
@Service
@Transactional
public class BillServiceImpl implements IBillService {

    @Autowired
    BillDAO billDAO;

    public DataListDTO getAll() {
        DataListDTO dataListDTO = new DataListDTO();
        try {
            List<Bill> list = billDAO.findAll();
            dataListDTO.setList(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }

    public static String formatCurrency(BigDecimal value) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        format.setMinimumFractionDigits(3);
        format.setMaximumFractionDigits(4);
        format.setRoundingMode(RoundingMode.HALF_EVEN);
        return format.format(value);
    }

    @Override
    public DataListDTO doSearch(BillDTO obj){
        DataListDTO dataListDTO = new DataListDTO();
        try {
            List<BillDTO> list = billDAO.getAllAction(obj);
            dataListDTO.setList(list);
            dataListDTO.setTotalPages(obj.getTotalRecord());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }
    public Long insert(BillDTO obj){
        Bill Bill = billDAO.findByFiled("id", obj.getId());
        if (Bill != null) {
            throw new ServiceException("Mã Hóa đơn đã tồn tại");
        }
        try {
            obj.setBuyingDate(new Timestamp(System.currentTimeMillis()));
            billDAO.insert(obj.toModel());
            return obj.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("Thêm mới hóa đơn không thành công");
        }
    }

    @Override
    public Long update(BillDTO obj){
        try {
            billDAO.update(obj.toModel());
            return obj.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }

    @Override
    public Long delete(Long id){
        try {
            Bill bill = billDAO.find(id);
            billDAO.delete(bill);
            return id;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }
}

