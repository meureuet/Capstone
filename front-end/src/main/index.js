import './index.css';

function MainPage() {
	return (
		<div>	
			<div id="login">
				<button type="button" id='homeBtn'>로그인</button>
				<button type="button" id='sellBtn'>회원가입</button>
			</div>
			<div id="header">
				<p id="title"><img src="images/mainLogo.png"></img>&nbsp;한성마켓</p>
			</div>
			<div id="nav">
				<div id="menu">
					<button type="button"><img src="images/menubar.png" width="20" height="20" /></button>
					<button type="button">홈</button>
					<button type="button">판매등록</button>
					<button type="button">상품검색</button>
					<button type="button">문의하기</button> 
				</div>
				<div id="user">
					<button type="button"><img src="images/user.png" width="20" height="20" /></button>
					<button type="button"><img src="images/cart.png" width="20" height="20" /></button>
					<button type="button"><img src="images/msg.png" width="20" height="20" /></button>
					<form action="uri" method="post"> {/*나중에 http, get/post로 uri설정 */}
						<input type="text" id='searchForm' size='20' name = "searchWord" placeholder="검색어를 입력하세요"></input> 
						<button type="submit"><img src="images/search.png" width="20" height="20" /></button>
					</form>
				</div>
			</div>
			<div id="banner">
				<img src="images/bannerImg.png" width="300"/> 
				<p><span id="bannerTitle">한성대학교만의 마켓 <br />
				한성마켓<br /> </span>
				<span id="bannerText"><br />전공서적부터 가구까지, 한성인과 함께해요. <br />가깝고 따뜻한 당신의 근처를 만들어요.</span></p>
			</div>
			<div id="body">
			</div>
		</div>
	);
}

export default MainPage;