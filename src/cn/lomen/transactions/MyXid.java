/** 
 * Project: test 
 * Package Name:transactions 
 * 
 * File Created at 2018年3月7日
 *
 * Copyright (c) 2018, Eastcom Technologies Co. Ltd 
 * All Rights Reserved. 
 *
 * This file contains proprietary information of Eastcom Technologies Co. Ltd.
 * Copying or reproduction without prior written approval is prohibited.
 * 
*/  
package cn.lomen.transactions;

import javax.transaction.xa.Xid;

import sun.text.resources.cldr.id.FormatData_id;

/** 
 * ClassName:MyXid <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年3月7日 上午8:40:28 <br/> 
 * @author   <a href="mailto:hujian@eastcom-sw.com">zhoum</a><br>
 * @version        
 */
public class MyXid implements Xid {

    private int formatId;
    

    public void setFormatId(int formatId) {
        this.formatId = formatId;
    }

    public byte[] getTxId() {
        return txId;
    }

    public void setTxId(byte[] txId) {
        this.txId = txId;
    }

    public byte[] getQualifier() {
        return qualifier;
    }
    public void setQualifier(byte[] qualifier) {
        this.qualifier = qualifier;
    }

    private byte[] qualifier;
    private byte[] txId;
    
    public  MyXid(int formatId,byte[] qualifier,byte[] txId) {
        this.formatId=formatId;
        this.qualifier=qualifier;
        this.txId=txId;
    }
    
    @Override
    public byte[] getBranchQualifier() {
        // TODO Auto-generated method stub
        return this.qualifier;
    }

    @Override
    public int getFormatId() {
        // TODO Auto-generated method stub
        return this.formatId;
    }

    @Override
    public byte[] getGlobalTransactionId() {
        // TODO Auto-generated method stub
        return this.txId;
    }

}
