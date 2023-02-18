import request from "@/utils/request"

// card list
export function cardList() {
    return request({
        url: "/cardList",
        method: "get"
    })
}

// card package list
export function packageList() {
    return request({
        url: "/cardPackageList",
        method: "get"
    })
}

// card list in card package
export function packageCardList(data) {
    return request({
        url: '/selectPackageCardList',
        method: 'post',
        data: data
    })
}

// create/update card
export function cardSave(data) {
    return request({
        url: '/cardSave',
        method: 'post',
        data: data
    })
}

// del card
export function delCard(data) {
    return request({
        url: '/deleteCardById',
        method: 'post',
        data: data
    })
}

// get card
export function getCard(data) {
    return request({
        url: '/getCardByCardId',
        method: 'post',
        data: data
    })
}