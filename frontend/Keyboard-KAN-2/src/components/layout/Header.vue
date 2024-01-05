<template>
  <header class="header">
    <div class="sites">
      <button type="button" class="siteBtn">
        <img :src="importedInstagram" alt="인스타그램" />
      </button>
      <button type="button" class="siteBtn">
        <img :src="importedDiscord" alt="디스코드" />
      </button>
      <button type="button" class="siteBtn">
        <img :src="importedNaver" alt="네이버" />
      </button>
      <button type="button" class="siteBtn">
        <img :src="importedYoutube" alt="유튜브" />
      </button>
    </div>
    <h1 class="title">
      <router-link to="/" class="titleLink">조선타자기</router-link>
    </h1>
    <div class="etc">
      <div class="searchWrapper">
        <input type="text" class="search" />
        <button type="button" class="searchBtn">
          <img :src="importedSearch" alt="검색" />
        </button>
      </div>
      <button type="button">
        <img :src="importedMyPage" alt="내정보" />
      </button>
      <button type="button">
        <img :src="importedBasket" alt="장바구니" />
      </button>
      <button type="button">
        <img :src="importedKorean" alt="한국어" />
      </button>
    </div>
  </header>
</template>

<script setup>
import { ref } from "vue";
import instagram from "@/assets/images/instagram.svg";
import discord from "@/assets/images/discord.svg";
import naver from "@/assets/images/naver.svg";
import youtube from "@/assets/images/youtube.svg";
import search from "@/assets/images/search.svg";
import myPage from "@/assets/images/mypage.svg";
import basket from "@/assets/images/basket.svg";
import korean from "@/assets/images/korean.svg";

const importedInstagram = ref(instagram);
const importedDiscord = ref(discord);
const importedNaver = ref(naver);
const importedYoutube = ref(youtube);
const importedSearch = ref(search);
const importedMyPage = ref(myPage);
const importedBasket = ref(basket);
const importedKorean = ref(korean);
</script>

<style scoped>
.header {
  position: relative;
  display: flex;
  justify-content: space-between;
  padding: 24px 60px;
  border-bottom: 1px solid #b3b3b3;
}

.sites {
  display: flex;
  gap: 10px;
}

.siteBtn {
  display: flex;
  justify-content: center;
  align-items: center;
}

.title {
  position: absolute;
  margin: 0 auto;
  font-size: 32.08px;
  font-weight: 700;
  line-height: 100%;
  transform: translate(-50%);
  left: 50%;
}

.titleLink {
  color: #000;
  text-decoration: none;
}

.titleLink:visited {
  color: #000;
}

.searchWrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  border-bottom: 1px solid #323232;
}

.search {
  border: none;
  outline: none;
}

.etc {
  display: flex;
  align-items: center;
  gap: 10px;
}
</style>
