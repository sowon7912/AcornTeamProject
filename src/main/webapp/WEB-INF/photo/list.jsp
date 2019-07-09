<%@page import="java.io.Console"%>
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
<c:set var="root" value="<%=request.getContextPath() %>"/>
<script type="text/javascript" src="${root}/js/photo/photolist.js"></script>
<link rel="stylesheet" href="${root}/css/photo/photo_list.css">
<meta name="viewport" content="width=device-width, user-scalable=no"/>
</head>
<body>
	<main id="root" role="main">
		<div class="container card-feed">
			<div class="sticky-container card-feed__filter-container" style="position: sticky; top: 0px;">
				<div class="sticky-child card-feed__filter-wrap" style="position: sticky;">
					<div class="filter card-feed__filter">
						<div class="filter-bar">
							<div class="filter-bar_control-list">
								<ul class="filter-bar__control-list__left">
									<li class="filter-bar__control-list__item">
										<div class="drop-down panel-drop-down filter-bar-control">
											<button class="button button--color-gray-4 button--size-50 button--shape-4 filter-bar-control__button">정렬
											<img class="icon" src="${root }/image/down-arrow.png"></button>
											<div class="drop-down-panel">
												<ul class="panel-entry-list">
													<li class="panel-entry-list__item-wrap">
														<button class="panel-entry-list__item" type="button">
															<div class="panel-entry-list__item__header">
																<span class="panel-entry-list__item__title" idx="1">인기순</span>
															</div>
														</button>
													</li>
													<li class="panel-entry-list__item-wrap">
														<button class="panel-entry-list__item" type="button">
															<div class="panel-entry-list__item__header">
																<span class="panel-entry-list__item__title" idx="2">최신순 </span>
															</div>
														</button>
													</li>
												</ul>
											</div>
										</div>
									</li>
									<li class="filter-bar__control-list__item">
										<div class="drop-down panel-drop-down filter-bar-control">
											<button class="button button--color-gray-4 button--size-50 button--shape-4 filter-bar-control__button">주거형태
											<img class="icon" src="${root }/image/down-arrow.png"></button>
											<div class="drop-down-panel">
												<ul class="panel-entry-list">
													<li class="panel-entry-list__item-wrap">
														<button class="panel-entry-list__item" type="button">
															<div class="panel-entry-list__item__header">
																<span class="panel-entry-list__item__title" idx="3">원룸</span>
															</div>
														</button>
													</li>
													<li class="panel-entry-list__item-wrap">
														<button class="panel-entry-list__item" type="button">
															<div class="panel-entry-list__item__header">
																<span class="panel-entry-list__item__title" idx="4">투룸</span>
															</div>
														</button>
													</li>
												</ul>
											</div>
										</div>
									</li>
									<li class="filter-bar__control-list__item">
										<div class="drop-down panel-drop-down filter-bar-control">
											<button class="button button--color-gray-4 button--size-50 button--shape-4 filter-bar-control__button">평수
											<img class="icon" src="${root }/image/down-arrow.png"></button>
											<div class="drop-down-panel">
												<ul class="panel-entry-list">
													<li class="panel-entry-list__item-wrap">
														<button class="panel-entry-list__item" type="button">
															<div class="panel-entry-list__item__header">
																<span class="panel-entry-list__item__title" idx="5">10평이하</span>
															</div>
														</button>
													</li>
													<li class="panel-entry-list__item-wrap">
														<button class="panel-entry-list__item" type="button">
															<div class="panel-entry-list__item__header">
																<span class="panel-entry-list__item__title" idx="6">20평이하</span>
															</div>
														</button>
													</li>
												</ul>
											</div>
										</div>
									</li>
								</ul>
							</div>
							<ul class="filter-bar_tag-list"></ul>
						</div>
					</div>
				</div>
			</div>
			<div class="virtualized-list card-feed__content row" style="padding-top: 0px;">
			<c:forEach var="pvo" items="${list}">
				<div class="card-feed__item-wrap col-12 col-md-4 col-lg-3">
					<div class="card-feed_item">
						<article class="card-item">
							<c:forEach var="mvo" items="${mlist}">
							<c:if test="${pvo.member_no == mvo.member_no}">
							<div class="card-item-writer">
								<address class="card-item-writer_content">
									<div class="card-item-writer_header">
										<div class="card-item-writer_link">
											<c:choose>
											<c:when test="${mvo.member_image eq null}">
											<img class="card-item-writer_image" src="${root }/image/user.png">
											</c:when>
											<c:otherwise>
											<img class="card-item-writer_image" src="${root }/image/${mvo.member_image}">
											</c:otherwise>
											</c:choose>
											<span class="card-item-writer_name">${mvo.member_nickname }</span>
										</div>
									</div>
									<p class="card-item-writer_introduction">${mvo.member_comment }
								</address>
							</div>
							</c:if>
							</c:forEach>
							<div class="card-item_content">
								<div class="card-item-image">
									<img class="image" src="${root }/image/room1.png" onclick="location.href='photodetail.do?num=${pvo.photo_seq_no }'">
									<span class="card-item-image_view-count">
										조회수 ${pvo.photo_hits }
									</span>
								</div>
								<aside class="card-item-action-list">
									<button class="card-item-action-list__action" type="button">
										<img class="icon icon--stroke" src="${root }/image/heart.png" width="24" height="24">
										<span class="count">${pvo.photo_good }</span>
									</button>
									<button class="card-item-action-list__action" type="button">
										<img class="icon icon--stroke" src="${root }/image/common/bookmark.png" width="24" height="24">
										<span class="count">1</span>
									</button>
									<button class="card-item-action-list__action" type="button">
										<img class="icon icon--stroke" src="${root }/image/comment.png" width="24" height="24">
										<span class="count"></span>
									</button>
								</aside>
								<div class="expandable-text card-item-description card-item__description">
									<div class="card-item-description__content">
										${pvo.photo_content }
									</div>
								</div>
							</div>
							<c:forEach var="cvo" items="${clist }" end="0">
							<c:if test="${pvo.photo_seq_no == cvo.p_reply_no }">
							<div class="card-item-comment-wrap">
								<article class="card-item-comment">
									<address class="card-item-comment__writer">
										<a class="card-item-comment__writer__link">
											<c:if test="${cvo.member_image eq null }">
											<img class="card-item-comment__writer__image" src="${root }/image/user.png">
											</c:if>
											<c:if test="${cvo.member_image ne null }">
											<img class="card-item-comment__writer__image" src="${root }/image/${cvo.member_image}">
											</c:if>
											<span class="card-item-comment__writer__name">${cvo.member_nickname}</span>
										</a>
										<span class="card-item-comment__writer__separator">:</span>
									</address>
									<a class="card-item-comment__link">
										<p class="card-item-comment__content">${cvo.p_reply_content }</p>
									</a>
								</article>
							</div>
							</c:if>
							</c:forEach>
						</article>
					</div>
				</div>
				</c:forEach>
			</div>
		</div>
	</main>
</body>
</html>
