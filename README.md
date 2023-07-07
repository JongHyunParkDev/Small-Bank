# SPPD
Spring batch + JPA + PostgreSQL + 공공데이터 (SPPD)

# 밴치마킹
- 가계부
  - 심플 가계부 - 설명이 필요없는 간단한 가계부
- 부동산 (공공데이터)
  - OpenStreetMap
  - 국토 교통부 실거래가 http://rtdown.molit.go.kr/ (API 쓰지 않아도 될 수도?), 초기 데이터 말 때 사용
  - 아파트 위경도 값이 있으면 좋겠다... 일단 이걸로 ([빅밸류](https://www.findatamall.or.kr/fsec/dataProd/generalDataProdDetail.do?cmnx=44&goods_id=b1dc08c0-6d9a-11ea-add2-c3b93f9f0784))
  - 검토중 http://data.nsdi.go.kr/dataset

# git
- [#이슈번호] 이슈 이름 으로 commit 한다.
- 한 브랜치에 여러 commit 을 허용한다. (여러 커밋 있어도 코드 리뷰를 한 번에 가능함)
  - github 에서는 rebase squash 시 commit 히스토리를 확인 불가능 한 것으로 보인다.  
