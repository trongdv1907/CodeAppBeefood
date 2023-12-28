package com.Users.beefood.model.EventBus;

//import com.example.beefood.model.DonHang;

import com.Users.beefood.model.DonHang;

public class DonHangEvent {
    DonHang donHang;

    public DonHangEvent(DonHang donHang) {
        this.donHang = donHang;
    }

    public DonHang getDonHang() {
        return donHang;
    }

    public void setDonHang(DonHang donHang) {
        this.donHang = donHang;
    }
}
