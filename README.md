# ERP

## host - container port forwarding
172.30.1.30:10021:21 - ftp

172.30.1.30:10022:22 - sftp, ssh

172.30.1.30:10033:3306 - mysql



## user name
host user : erpmaster

container user : erpin

mysql user : erpin

## 프로젝트 작성방식
사용기술 : Spring-boot, JPA, Thymeleaf, MySQL

### Java 네이밍(예시)
1. 패키지는 소문자
2. 클래스는 파스칼(첫글자 대문자, 구분 대문자) 사용
3. 메소드는 카멜 표기법(첫글자 소문자, 구분 대문자) 사용
4. 변수는 소문자 or 카멜
5. 상수는 대문자와 언더바를 사용 (private static final)
   
#### <패키지>
컨트롤러 : com.erp.controller  
엔티티 : com.erp.entity  
DTO : com.erp.dto  
레포지토리 : com.erp.repository  (데이터 접근 로직)
서비스 : com.erp.service   (비즈니스 로직)

#### <클래스>  
LoginController / LoginRestController  
LoginService / LoginServiceImpl  
LoginRepository / LoginRepositoryImpl  
LoginEntity  

#### <메소드>  
getUserId  
addUser  
addProduct  
getProduct  
 
#### <변수>  
String database  
int count  
@Autowired  
private LoginRepository loginRepository  
private static final String USER_KEY  

#### Web 네이밍(상의후 결정)  
HTML : loginJoin or login_join or LoginJoin    
JS : loginJoin.js or login_join.js or LoginJoin.js  
CSS :  JS와 동일  

#### 파일 위치  
HTML : src/main/resources/teplates/product   
JS or CSS : src/main/resources/static/css or js/product.js or product.css  

#### 환경
tomcat :10.1.28  
java :17.0.11  
maven 4.0.0  
springframework 3.2.8  
mysql community server 8.4.2  

## 역할
신희문 : 로그인 + 주문등록+ 반품.  
명동건 : 입고 + 납품 + 발주처목록 + 발주처 품목.  
김하주 : 출고 + 거래처 + 물품 + 주문승인  
김선숙 : 재고관리 + 창고 로케이션 관리
