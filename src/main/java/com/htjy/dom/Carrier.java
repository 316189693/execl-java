package com.htjy.dom;

/**
 * Created by willz on 9/22/2017.
 */
public class Carrier {
    private int carrierFamilyId;
    private String carrierFamilyName;
    private int genericCarrierId;
    private String CarrierName;
    private String state;
    private String productLine;
    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public int getCarrierFamilyId() {
        return carrierFamilyId;
    }

    public void setCarrierFamilyId(int carrierFamilyId) {
        this.carrierFamilyId = carrierFamilyId;
    }

    public String getCarrierFamilyName() {
        return carrierFamilyName;
    }

    public void setCarrierFamilyName(String carrierFamilyName) {
        this.carrierFamilyName = carrierFamilyName;
    }

    public int getGenericCarrierId() {
        return genericCarrierId;
    }

    public void setGenericCarrierId(int genericCarrierId) {
        this.genericCarrierId = genericCarrierId;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCarrierName() {
        return CarrierName;
    }

    public void setCarrierName(String carrierName) {
        CarrierName = carrierName;
    }

    @Override
    public String toString() {
        return "Carrier{" +
                "carrierFamilyId=" + carrierFamilyId +
                ", carrierFamilyName='" + carrierFamilyName + '\'' +
                ", genericCarrierId=" + genericCarrierId +
                ", carrierName='" + CarrierName + '\'' +
                ", state='" + state + '\'' +
                ", productLine='" + productLine + '\'' +
                '}';
    }
}
