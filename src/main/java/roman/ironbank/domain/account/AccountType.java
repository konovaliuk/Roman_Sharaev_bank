package roman.ironbank.domain.account;

public enum AccountType {
    CREDIT{
        public  String toString(){
            return "credit";
        }
    },
    DEPOSIT{
        public String toString(){
            return "deposit";
        }
    }
}
