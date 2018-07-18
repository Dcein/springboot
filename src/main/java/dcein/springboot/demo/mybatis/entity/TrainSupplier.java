package dcein.springboot.demo.mybatis.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "train_supplier")
public class TrainSupplier implements Serializable {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private Long id;

    /**
     * 供应商编号
     */
    @Column(name = "SUPPLIER_NO")
    private String supplierNo;

    /**
     * 供应商名称
     */
    @Column(name = "SUPPLIER_NAME")
    private String supplierName;

    /**
     * 描述
     */
    @Column(name = "SUPPLIER_DESC")
    private String supplierDesc;

    /**
     * 地址
     */
    @Column(name = "ADDRESS")
    private String address;

    /**
     * 联系人
     */
    @Column(name = "CONTACTS")
    private String contacts;

    /**
     * 手机号
     */
    @Column(name = "MOBILE_NO")
    private String mobileNo;

    @Column(name = "SUPPLIER_TYPE")
    private String supplierType;

    /**
     * 供应商公钥
     */
    @Column(name = "SUPPLIER_PUB_KEY")
    private String supplierPubKey;

    /**
     * 平台公钥
     */
    @Column(name = "SIP_PUB_KEY")
    private String sipPubKey;

    /**
     * 平台私钥
     */
    @Column(name = "SIP_PRI_KEY")
    private String sipPriKey;

    @Column(name = "SUPPLIER_FLAG")
    private String supplierFlag;

    /**
     * 创建人
     */
    @Column(name = "CREATE_BY")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_DATE")
    private Date createDate;

    /**
     * 修改人
     */
    @Column(name = "UPDATE_BY")
    private String updateBy;

    /**
     * 修改时间
     */
    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    private static final long serialVersionUID = 1L;

    /**
     * 获取ID
     *
     * @return ID - ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取供应商编号
     *
     * @return SUPPLIER_NO - 供应商编号
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * 设置供应商编号
     *
     * @param supplierNo 供应商编号
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * 获取供应商名称
     *
     * @return SUPPLIER_NAME - 供应商名称
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 设置供应商名称
     *
     * @param supplierName 供应商名称
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 获取描述
     *
     * @return SUPPLIER_DESC - 描述
     */
    public String getSupplierDesc() {
        return supplierDesc;
    }

    /**
     * 设置描述
     *
     * @param supplierDesc 描述
     */
    public void setSupplierDesc(String supplierDesc) {
        this.supplierDesc = supplierDesc;
    }

    /**
     * 获取地址
     *
     * @return ADDRESS - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取联系人
     *
     * @return CONTACTS - 联系人
     */
    public String getContacts() {
        return contacts;
    }

    /**
     * 设置联系人
     *
     * @param contacts 联系人
     */
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    /**
     * 获取手机号
     *
     * @return MOBILE_NO - 手机号
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * 设置手机号
     *
     * @param mobileNo 手机号
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * @return SUPPLIER_TYPE
     */
    public String getSupplierType() {
        return supplierType;
    }

    /**
     * @param supplierType
     */
    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    /**
     * 获取供应商公钥
     *
     * @return SUPPLIER_PUB_KEY - 供应商公钥
     */
    public String getSupplierPubKey() {
        return supplierPubKey;
    }

    /**
     * 设置供应商公钥
     *
     * @param supplierPubKey 供应商公钥
     */
    public void setSupplierPubKey(String supplierPubKey) {
        this.supplierPubKey = supplierPubKey;
    }

    /**
     * 获取平台公钥
     *
     * @return SIP_PUB_KEY - 平台公钥
     */
    public String getSipPubKey() {
        return sipPubKey;
    }

    /**
     * 设置平台公钥
     *
     * @param sipPubKey 平台公钥
     */
    public void setSipPubKey(String sipPubKey) {
        this.sipPubKey = sipPubKey;
    }

    /**
     * 获取平台私钥
     *
     * @return SIP_PRI_KEY - 平台私钥
     */
    public String getSipPriKey() {
        return sipPriKey;
    }

    /**
     * 设置平台私钥
     *
     * @param sipPriKey 平台私钥
     */
    public void setSipPriKey(String sipPriKey) {
        this.sipPriKey = sipPriKey;
    }

    /**
     * @return SUPPLIER_FLAG
     */
    public String getSupplierFlag() {
        return supplierFlag;
    }

    /**
     * @param supplierFlag
     */
    public void setSupplierFlag(String supplierFlag) {
        this.supplierFlag = supplierFlag;
    }

    /**
     * 获取创建人
     *
     * @return CREATE_BY - 创建人
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return CREATE_DATE - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取修改人
     *
     * @return UPDATE_BY - 修改人
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置修改人
     *
     * @param updateBy 修改人
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取修改时间
     *
     * @return UPDATE_DATE - 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置修改时间
     *
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}