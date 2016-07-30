/* back end models definition */
{
    Commodity:{
        id:"9843928949032994b432990034284320",
        name:'Adidas Dress',
        price:1128.00,
        gender:"female",
        category:"clothes",
        thumbnail:"thumbnail.jpg",
        portfolio:["a.jpg","b.jpg","c.jpg"],
        currency:"USD",
        size:"L",
        descriptions:["bla bla bla...","http://xx.jpg","bla bla bla...","http://xx.jpg","bla bla bla...","http://xx.jpg"],
        color:"pink",
        comments:[Comments]
    },
    Comment:{
        logo:"xxx.png"(query from userId)
        username:"gordon rawe"(query from userId)
        id:"9843928949032994b432990034284320",
        time:"2016-04-15-15:22",
        userId:"9843928949032994b432990034284320",
        content:'bla bla bla...',
        pictures:["a.jpg",'b.jpg']
    },
    User:{
        username:"gordon rawe",
        password:"bla bla bla...",
        email:"gordon.tongji@hotmail.com",
        address:Address,
        phone:"15121030486",
        deliverAddresses:[Addresses],
        creditNumber:"B324899A983243923489003"
    },
    Address:{
        province:"Shanghai",
        city:"shanghai",
        street:"river road.",
        detail:"some detail description",
        zip:200393
    },
    Order:{
        commodityId:"9843928949032994b432990034284320"(required),
        amount:3,
        thumbnail:"x.jpg"(query from userId),
        size:"L"(query from userId),
        price:"$321.99",
        title:"Adidas Shoes"
    }
}
{
    BasicDefinitions:{
        currency:[USD,CNY,JPN,THD,EUR...],
        gender:[male,female,kids,all],
        color:[dark,pink,green,light red,sky blue...],
        size:[XXS,XS,S,M,L,XL,XXL,3XL],
        brand:[adidas,nike,whitney,michael]
    }
}

/* front end models interfaces design */
{
    Commodity:{
        commodityId:"9843928949032994b432990034284320"
        name:'Adidas Dress',
        price:1128.00,
        gender:"female",
        category:"clothes",
        thumbnail:"thumbnail.jpg",
        portfolio:["a.jpg","b.jpg","c.jpg"],
        currency:"USD",
        size:"L",
        descriptions:["bla bla bla...","http://xx.jpg","bla bla bla...","http://xx.jpg","bla bla bla...","http://xx.jpg"],
        color:"pink",
        comments:[comments]
    },
    Comment:{
        commentId:"9843928949032994b432990034284320"
        userThumbnail:"xxx.png"
        username:"gordon rawe"
        time:"2016-04-15-15:22",
        content:'bla bla bla...',
        pictures:["a.jpg",'b.jpg']
    },
    User:{
        userId:"9843928949032994b432990034284320",
        username:"gordon rawe",
        password:"bla bla bla...",
        email:"gordon.tongji@hotmail.com",
        address:Address,
        phone:"15121030486",
        deliverAddresses:[Addresses],
        creditNumber:"B324899A983243923489003"
    },
    Address:{
        province:"Shanghai",
        city:"shanghai",
        street:"river road.",
        detail:"some detail description",
        zip:200393,
        phone:15121030486
    },
    Order:{
        orderId:"9843928949032994b432990034284320",
        amount:3,
        thumbnail:"x.jpg",
        size:"L",
        price:"$321.99",
        title:"Adidas Shoes",
        commodityId:"9843928949032994b432990034284320"(user for url jump)
    }
}
{
    BasicDefinitions:{
        currency:[USD,CNY,JPN,THD,EUR...],
        gender:[male,female,kids,all],
        color:[dark,pink,green,light red,sky blue...],
        size:[XXS,XS,S,M,L,XL,XXL,3XL],
        brand:[adidas,nike,whitney,michael]
    }
}
