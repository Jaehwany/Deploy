# Jenkins 설치

<br>

1. jenkins 설치

```
$ make run
```
<br>

2. 권한 문제 확인하기 (90% 확률로 volume에 대한 문제 발생)

```
$ docker logs jenkins
```
<br>

3. jenkins volume 권한 주기 (문제가 발생한 volume에 대한 permission 허가)  ${error_volume}

```
$ sudo chmod 777 volume
```
<br>


4. jenkins docker 재실행
```
$ docker stop jenkins
$ docker rm jenkins
$ make run
```
