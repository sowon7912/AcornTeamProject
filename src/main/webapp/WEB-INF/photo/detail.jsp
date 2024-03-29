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
<c:set var="root" value="<%=request.getContextPath()%>"/>
<script type="text/javascript" src="${root}/js/photo/photodetail.js"></script>
<link rel="stylesheet" href="${root}/css/photo/photo_detail.css">
<link rel="shortcut icon" href="#">
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic|Noto+Sans+KR&display=swap" rel="stylesheet">
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
						<c:forEach items="${plist }" var="photo">
						<div class="card-img card">
							<div class="card-img__bg" style="padding-top: 65%"></div>
							<img src="${photo.photo_image}">
						</div>
						<figcaption>
							<p>${photo.photo_content }</p>
						</figcaption>
						</c:forEach>
					</figure>
					<ul class="keyword">
						<c:forEach items="${plist }" var="photo">
						<li class="keyword__item">
							<div class="keyword__item__badge">
							#${photo.hashtag }
							</div>
						</li>
						</c:forEach>
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
						<div class="comment-feed__form" id="commentform">
							<input type="hidden" name="member_no" value="${sessionScope.mvo.member_no }"/>
							<input type="hidden" name="photo_seq_no" value="${pvo.photo_seq_no }"/>
							<div class="comment-feed__form__user">
								<c:if test="${sessionScope.mvo.member_image ne null}">
									<img src="${root }/uploadImage/${sessionScope.mvo.member_image }">
								</c:if>
								<c:if test="${sessionScope.mvo.member_image eq null}">
	                    			<img src="${root}/image/common/user.png">
              					</c:if>
							</div>
							<div class="comment-feed__form__input">
								<div class="comment-feed__form__content">
									<div class="comment-content-input">
										<input type="text" class="comment-content-input__text comment-feed__form__content__text" id="comment">
									</div>
								</div>
								<div class="comment-feed__form__actions">
									<button class="comment-feed__form__submit" type="button"></button>
								</div>
							</div>
						</div>
						<ul class="comment-feed__list">
						<div id="comment_append" session-data-member="${sessionScope.member_no}" data-member="${mvo.member_no }">
							<%-- <c:forEach var="cvo" items="${cvo }">
							<li class="comment-feed__list__item">
								<article class="comment-feed__item">
									<p class="comment-feed__item__content">
										<a class="comment-feed__item__content__author">
											<c:if test="${cvo.member_image eq null}"> 
											<img class="comment-feed__item__content__author__image" src="${root }/image/common/user.png">
											</c:if>
											<c:if test="${cvo.member_image ne null}"> 
											<img class="comment-feed__item__content__author__image" src="${root }/image/common/${cvo.member_image}">
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
							</c:forEach> --%>
						</div>
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
													<img src="../image/common/heart.png" width="24" height="24">
													</span>
													${pvo.photo_good }
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
														<img class="card-item-writer_image" src="${root }/image/common/user.png">
														</c:when>
														<c:otherwise>
														<img src="${root }/uploadImage/${mvo.member_image}">
														</c:otherwise>
														</c:choose>
													</a>
												</div>
												<div style="width:30%;">
													<a href="">
														<strong class="writer-profile__name">${mvo.member_nickname }</strong>
													</a>
													<a href="">
														<p class="writer-profile__about">${mvo.member_comment }</p>
													</a>
												</div>
												<c:if test="${mvo.member_no eq sessionScope.mvo.member_no}">
												<div class="sidebar__update_delete">
													<a href="updateform.do?num=${pvo.photo_seq_no}" class="sidebar__update_button">
														수정
													</a>
													<a href="delete.do?photo_seq_no=${pvo.photo_seq_no}" class="sidebar__delete_button">
														삭제
													</a>
												</div>
												</c:if>
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