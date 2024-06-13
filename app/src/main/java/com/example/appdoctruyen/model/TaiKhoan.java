package com.example.appdoctruyen.model;

public class TaiKhoan {

    //các biến
    private int mId;
    private String mTenTaiKhoan;
    private String mMatKhau;
    private String mEmail;
    private int PhanQuyen;

    //hàm khởi tạo
    public TaiKhoan(String mTenTaiKhoan, String mMatKhau, String mEmail, int phanQuyen) {
        this.mTenTaiKhoan = mTenTaiKhoan;
        this.mMatKhau = mMatKhau;
        this.mEmail = mEmail;
        PhanQuyen = phanQuyen;
    }

    public TaiKhoan(String mTenTaiKhoan, String mEmail) {
        this.mTenTaiKhoan = mTenTaiKhoan;
        this.mEmail = mEmail;
    }

    //get và set
    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmTenTaiKhoan() {
        return mTenTaiKhoan;
    }

    public void setmTenTaiKhoan(String mTenTaiKhoan) {
        this.mTenTaiKhoan = mTenTaiKhoan;
    }

    public String getmMatKhau() {
        return mMatKhau;
    }

    public void setmMatKhau(String mMatKhau) {
        this.mMatKhau = mMatKhau;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public int getPhanQuyen() {
        return PhanQuyen;
    }

    public void setPhanQuyen(int phanQuyen) {
        PhanQuyen = phanQuyen;
    }
}
