# Instamilligram API
인스타밀리그램을 구현하는데 필요한 기능을 제공해줄 API 서버입니다.
회원, 게시글, 댓글 서비스를 제공합니다.
- 회원: 로그인, 회원가입, 회원정보 조회
- 게시글: 게시글 조회, 게시글 만들기, 게시글 좋아요, 게시글 좋아요 취소, 게시글의 댓글 조회
- 댓글: 댓글의 답글 조회, 댓글 좋아요, 댓글 좋아요 취소

# 1. Swagger로 API 간편하게 테스트하기
<img src="https://github.com/user-attachments/assets/847dd1b3-cac3-4efc-a79a-576c8f3d5036" width="800">

### Swagger 링크
https://zerobaseapi.winten.im/swagger-ui/index.html#/

### 기본 사용법
1. 가입(```/users```)과 로그인(```/login```)을 제외한 **모든 API는 엑세스 토큰이 필수** 입니다
2. 신규 가입 방법
POST ```/users``` API를 사용해서 회원가입이 가능합니다. 원활한 테스트를 위해 가능한 많은 값을 입력해주세요.
3. 로그인 방법
POST ```/login``` API를 사용하면 가입한 아이디와 패스워드로 인증이 가능합니다. 성공적으로 로그인하게 되면 엑세스토큰이 발급됩니다.
4. 이후 API 요청 시 Authroization 헤더에 엑세스토큰을 추가합니다.

```javascript
headers = {
	"Authorization": "Bearer eyJhbGciOiJIUzI...." //Bearer 엑세스토큰
}
```


# 2. CDN서버에서 게시글 이미지 불러오기
게시글 생성 API를 통해 이미지를 업로드하게 되면 업로드한 이미지가 CDN 서버에 저장됩니다.
반환받은 이미지 id를 사용해서 이미지를 조회할 수 있습니다.

### Swagger 주소
https://cdn.winten.im/docs

### 조회방법 예시
```html
<!-- "https://cdn.winten.im/api/zerobase/file/**{이미지명}" -->**

<img src="https://cdn.winten.im/api/zerobase/file/p_1_0_taH6Xe4H.jpg" />
```

# 3. 아키텍처가 궁금한 분들을 위해
<img src="https://github.com/user-attachments/assets/7ba05915-1e17-4aea-80e8-fb31f03737f3" width="800" />
