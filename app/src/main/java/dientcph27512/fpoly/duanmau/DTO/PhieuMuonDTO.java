package dientcph27512.fpoly.duanmau.DTO;

import java.util.Date;

public class PhieuMuonDTO {
    private int maPM;
    private String maTT;
    private int maTV;
    private int maSach;
    private int tienThue;
    private int traSach;
    private Date ngay;

    public PhieuMuonDTO() {
    }

    public PhieuMuonDTO(String maTT, int maTV, int maSach) {
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
    }

    public PhieuMuonDTO(String maTT, int maTV, int maSach, int tienThue, int traSach, Date ngay) {
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.traSach = traSach;
        this.ngay = ngay;
    }

    public PhieuMuonDTO(int maPM, String maTT, int maTV, int maSach, int tienThue, int traSach, Date ngay) {
        this.maPM = maPM;
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.traSach = traSach;
        this.ngay = ngay;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }
}
