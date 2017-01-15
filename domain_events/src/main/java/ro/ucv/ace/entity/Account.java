package ro.ucv.ace.entity;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;

@NodeEntity
public class Account implements Serializable {

    private static final long serialVersionUID = -8860106787025445177L;

    @GraphId
    private Long accountId;

    private String accountType;

    private Double balance;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account [accountId=" + accountId + ", accountType=" + accountType + ", balance=" + balance + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
        result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
        result = prime * result + ((balance == null) ? 0 : balance.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Account other = (Account) obj;
        if (accountId == null) {
            if (other.accountId != null) {
                return false;
            }
        } else if (!accountId.equals(other.accountId)) {
            return false;
        }
        if (accountType == null) {
            if (other.accountType != null) {
                return false;
            }
        } else if (!accountType.equals(other.accountType)) {
            return false;
        }
        if (balance == null) {
            if (other.balance != null) {
                return false;
            }
        } else if (!balance.equals(other.balance)) {
            return false;
        }
        return true;
    }


}
