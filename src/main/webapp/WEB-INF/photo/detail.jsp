<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>     
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic|Noto+Sans+KR&display=swap" rel="stylesheet">
<c:set var="root" value="<%=request.getContextPath()%>"/>
<link rel="stylesheet" href="${root}/css/photo/photo_detail.css">
<link rel="shortcut icon" href="#">
</head>
<body>
<main id="root" role="main">
	<div class="card-detail container">
		<article class="row">
			<section class="col-12 col-lg-8">
				<header>
					<p class="category">
						<span class="category__item">${pvo.photo_pyeong }</span>
						<span class="category__item">${pvo.photo_hometype }</span>
					</p>
					<time>어제</time>
				</header>
				<section class="card-section">
					<figure>
						<div class="card-img card">
							<div class="card-img__bg" style="padding-top: 65%"></div>
							<img src="${root }/image/room1.png">
						</div>
						<figcaption>
							<p>${pvo.photo_content }</p>
						</figcaption>
					</figure>
					
					<ul class="keyword">
						<li class="keyword__item">
							<div class="keyword__item__badge">${pvo.hashtag }</div>
						</li>
					</ul>
					
				</section>
				<section class="footer">
					<div class="footer__stats">
						<div class="footer__stats__item">
							<span>
							조회  ${pvo.photo_hits }
							</span>
						</div>
						<div class="footer__stats__item">
							<span>
							댓글 ${fn:length(cvo)}
							</span>
						</div>
					</div>
					<hr class="section-divider">
				</section>
				<div>
					<section class="comment-feed">
						<h1 class="comment-feed__header">댓글
							<span class="comment-feed__header__count">${fn:length(cvo)}</span>
						</h1>
						<form class="comment-feed__form">
							<div class="comment-feed__form__user">
								<c:if test="${sessionScope.mvo.member_image ne null}">
									<img src="${root }/image/${sessionScope.mvo.member_image }">
								</c:if>
								<c:if test="${sessionScope.mvo.member_image eq null}">
	                    			<img src="${root}/image/user.png">
	                    		</c:if>
							</div>
							<div class="comment-feed__form__input">
								<div class="comment-feed__form__content">
									<div class="comment-content-input">
										<input type="text" class="comment-content-input__text comment-feed__form__content__text">
									</div>
								</div>
								<div class="comment-feed__form__actions">
									<button class="comment-feed__form__submit" type="submit" disabled="disabled"></button>
								</div>
							</div>
						</form>
						<ul class="comment-feed__list">
							<c:forEach var="cvo" items="${cvo }">
							<li class="comment-feed__list__item">
								<article class="comment-feed__item">
									<p class="comment-feed__item__content">
										<a class="comment-feed__item__content__author">
											<c:if test="${cvo.member_image eq null}"> 
											<img class="comment-feed__item__content__author__image" src="${root }/image/user.png">
											</c:if>
											<c:if test="${cvo.member_image ne null}"> 
											<img class="comment-feed__item__content__author__image" src="${root }/image/${cvo.member_image}">
											</c:if>
											<span class="comment-feed__item__content__author__name">${cvo.member_nickname }</span>
										</a>
										<span class="comment-feed__item__content__content">${cvo.p_reply_content }</span>	
									</p>
									<footer class="comment-feed__item__footer">
										<time class="comment-feed__item__footer__time">
										<fmt:formatDate pattern="YYYY-MM-dd" value="${cvo.reg_date }"/> 
										</time>
									</footer>
								</article>
							</li>
							</c:forEach>
						</ul>
						<ul class="list-paginator">
							<li>
							
							</li>
						</ul>
					</section>
				</div>
			</section>
			<aside class="col-4 sidebar">
						<div class="sticky-container sidebar__sticky" style="position:sticky; top:132px;">
							<div class="sticky-child" style="position: relative;">
								<div class="sidebar__container" style="height: 750px;">
									<div style="padding-top: 40px;">
										<section class="sidebar__action">
											<div>
												<button class="sidebar__action__btn" type="button">
													<span class="icon--common-action" style="vertical-align: middle; 
													margin-right: 8px; background-position: -240px -280px; width: 24px; height: 24px;">
													<img src="../image/heart.png" width="24" height="24">
													</span>
													1
												</button>
											</div>
											<div>
												<button class="sidebar__action__btn" type="button">
													<span class="icon--common-action" style="vertical-align: middle; 
													margin-right: 8px; background-position: -240px -280px; width: 24px; height: 24px;">
													<img src="../image/common/bookmark.png" width="24" height="24">
													</span>
													1
												</button>
											</div>
										</section>
										<section class="sidebar__writer writer-info">
											<div class="writer-profile">
												<div class="writer-profile__img">
													<a href="">
														<c:choose>
														<c:when test="${mvo.member_image eq null }">
														<img class="card-item-writer_image" src="${root }/image/user.png">
														</c:when>
														<c:otherwise>
														<img src="${root }/image/${mvo.member_image}">
														</c:otherwise>
														</c:choose>
													</a>
												</div>
												<div>
													<a href="">
														<strong class="writer-profile__name">${mvo.member_nickname }</strong>
													</a>
													<a href="">
														<p class="writer-profile__about">${mvo.member_comment }</p>
													</a>
												</div>
											</div>
										</section>
										<section class="sidebar_others">
											<div class="sidebar__others__list">
												<div class="sidebar__others__thumbnail">
													<div class="sidebar__others__thumbnail__img">
														<a href="">
															<img src="">
														</a>
													</div>
												</div>
											</div>
										</section>
									</div>
								</div>
							</div>
						</div>
					</aside>
		</article>
	</div>
</main>
</body>
</html>
x