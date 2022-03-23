# wooaham_server
```
```

## AWS 접속방법
**1. 키페어를 다운받는다**
  - 노션에 저장해둘까?

**2. aws 로그인 후 ec2에 접속하여 인스턴스 실행중인지 확인한다 - 계속 켜놓긴 할거라 생략 가능할듯**

**3. 다음 과정 진행** 
```
  인스턴스 ID : i-019610d9e7da93076
  1.SSH 클라이언트를 엽니다.
  2.프라이빗 키 파일을 찾습니다. 이 인스턴스를 시작하는 데 사용되는 키는 wooaham-ec2-key.pem입니다.
    필요한 경우 이 명령을 실행하여 키를 공개적으로 볼 수 없도록 합니다.
    chmod 400 wooaham-ec2-key.pem
  3.퍼블릭 DNS을(를) 사용하여 인스턴스에 연결: ** 키페어가 있는 디렉토리로 이동한 후에 다음 명령어 실행
    ec2-3-35-134-157.ap-northeast-2.compute.amazonaws.com
    ssh -i "wooaham-ec2-key.pem" ec2-user@ec2-3-35-134-157.ap-northeast-2.compute.amazonaws.com
 ```

 
