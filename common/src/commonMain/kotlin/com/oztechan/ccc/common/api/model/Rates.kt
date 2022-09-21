/*
 * Copyright (c) 2021 Mustafa Ozhan. All rights reserved.
 */
package com.oztechan.ccc.common.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Suppress("ConstructorParameterNaming", "OPT_IN_USAGE")
@Serializable
internal data class Rates(
    @SerialName("base") var base: String = "",
    @SerialName("date") var date: String? = null,
    @JsonNames("aed", "AED") var aed: Double? = null,
    @JsonNames("afn", "AFN") var afn: Double? = null,
    @JsonNames("all", "ALL") var all: Double? = null,
    @JsonNames("amd", "AMD") var amd: Double? = null,
    @JsonNames("ang", "ANG") var ang: Double? = null,
    @JsonNames("aoa", "AOA") var aoa: Double? = null,
    @JsonNames("ars", "ARS") var ars: Double? = null,
    @JsonNames("aud", "AUD") var aud: Double? = null,
    @JsonNames("awg", "AWG") var awg: Double? = null,
    @JsonNames("azn", "AZN") var azn: Double? = null,
    @JsonNames("bam", "BAM") var bam: Double? = null,
    @JsonNames("bbd", "BBD") var bbd: Double? = null,
    @JsonNames("bdt", "BDT") var bdt: Double? = null,
    @JsonNames("bgn", "BGN") var bgn: Double? = null,
    @JsonNames("bhd", "BHD") var bhd: Double? = null,
    @JsonNames("bif", "BIF") var bif: Double? = null,
    @JsonNames("bmd", "BMD") var bmd: Double? = null,
    @JsonNames("bnd", "BND") var bnd: Double? = null,
    @JsonNames("bob", "BOB") var bob: Double? = null,
    @JsonNames("brl", "BRL") var brl: Double? = null,
    @JsonNames("bsd", "BSD") var bsd: Double? = null,
    @JsonNames("btc", "BTC") var btc: Double? = null,
    @JsonNames("btn", "BTN") var btn: Double? = null,
    @JsonNames("bwp", "BWP") var bwp: Double? = null,
    @JsonNames("byn", "BYN") var byn: Double? = null,
    @JsonNames("bzd", "BZD") var bzd: Double? = null,
    @JsonNames("cad", "CAD") var cad: Double? = null,
    @JsonNames("cdf", "CDF") var cdf: Double? = null,
    @JsonNames("chf", "CHF") var chf: Double? = null,
    @JsonNames("clf", "CLF") var clf: Double? = null,
    @JsonNames("clp", "CLP") var clp: Double? = null,
    @JsonNames("cnh", "CNH") var cnh: Double? = null,
    @JsonNames("cny", "CNY") var cny: Double? = null,
    @JsonNames("cop", "COP") var cop: Double? = null,
    @JsonNames("crc", "CRC") var crc: Double? = null,
    @JsonNames("cuc", "CUC") var cuc: Double? = null,
    @JsonNames("cup", "CUP") var cup: Double? = null,
    @JsonNames("cve", "CVE") var cve: Double? = null,
    @JsonNames("czk", "CZK") var czk: Double? = null,
    @JsonNames("djf", "DJF") var djf: Double? = null,
    @JsonNames("dkk", "DKK") var dkk: Double? = null,
    @JsonNames("dop", "DOP") var dop: Double? = null,
    @JsonNames("dzd", "DZD") var dzd: Double? = null,
    @JsonNames("egp", "EGP") var egp: Double? = null,
    @JsonNames("ern", "ERN") var ern: Double? = null,
    @JsonNames("etb", "ETB") var etb: Double? = null,
    @JsonNames("eur", "EUR") var eur: Double? = null,
    @JsonNames("fjd", "FJD") var fjd: Double? = null,
    @JsonNames("fkp", "FKP") var fkp: Double? = null,
    @JsonNames("gbp", "GBP") var gbp: Double? = null,
    @JsonNames("gel", "GEL") var gel: Double? = null,
    @JsonNames("ggp", "GGP") var ggp: Double? = null,
    @JsonNames("ghs", "GHS") var ghs: Double? = null,
    @JsonNames("gip", "GIP") var gip: Double? = null,
    @JsonNames("gmd", "GMD") var gmd: Double? = null,
    @JsonNames("gnf", "GNF") var gnf: Double? = null,
    @JsonNames("gtq", "GTQ") var gtq: Double? = null,
    @JsonNames("gyd", "GYD") var gyd: Double? = null,
    @JsonNames("hkd", "HKD") var hkd: Double? = null,
    @JsonNames("hnl", "HNL") var hnl: Double? = null,
    @JsonNames("hrk", "HRK") var hrk: Double? = null,
    @JsonNames("htg", "HTG") var htg: Double? = null,
    @JsonNames("huf", "HUF") var huf: Double? = null,
    @JsonNames("idr", "IDR") var idr: Double? = null,
    @JsonNames("ils", "ILS") var ils: Double? = null,
    @JsonNames("imp", "IMP") var imp: Double? = null,
    @JsonNames("inr", "INR") var inr: Double? = null,
    @JsonNames("iqd", "IQD") var iqd: Double? = null,
    @JsonNames("irr", "IRR") var irr: Double? = null,
    @JsonNames("isk", "ISK") var isk: Double? = null,
    @JsonNames("jep", "JEP") var jep: Double? = null,
    @JsonNames("jmd", "JMD") var jmd: Double? = null,
    @JsonNames("jod", "JOD") var jod: Double? = null,
    @JsonNames("jpy", "JPY") var jpy: Double? = null,
    @JsonNames("kes", "KES") var kes: Double? = null,
    @JsonNames("kgs", "KGS") var kgs: Double? = null,
    @JsonNames("khr", "KHR") var khr: Double? = null,
    @JsonNames("kmf", "KMF") var kmf: Double? = null,
    @JsonNames("kpw", "KPW") var kpw: Double? = null,
    @JsonNames("krw", "KRW") var krw: Double? = null,
    @JsonNames("kwd", "KWD") var kwd: Double? = null,
    @JsonNames("kyd", "KYD") var kyd: Double? = null,
    @JsonNames("kzt", "KZT") var kzt: Double? = null,
    @JsonNames("lak", "LAK") var lak: Double? = null,
    @JsonNames("lbp", "LBP") var lbp: Double? = null,
    @JsonNames("lkr", "LKR") var lkr: Double? = null,
    @JsonNames("lrd", "LRD") var lrd: Double? = null,
    @JsonNames("lsl", "LSL") var lsl: Double? = null,
    @JsonNames("lyd", "LYD") var lyd: Double? = null,
    @JsonNames("mad", "MAD") var mad: Double? = null,
    @JsonNames("mdl", "MDL") var mdl: Double? = null,
    @JsonNames("mga", "MGA") var mga: Double? = null,
    @JsonNames("mkd", "MKD") var mkd: Double? = null,
    @JsonNames("mmk", "MMK") var mmk: Double? = null,
    @JsonNames("mnt", "MNT") var mnt: Double? = null,
    @JsonNames("mop", "MOP") var mop: Double? = null,
    @JsonNames("mro", "MRO") var mro: Double? = null,
    @JsonNames("mru", "MRU") var mru: Double? = null,
    @JsonNames("mur", "MUR") var mur: Double? = null,
    @JsonNames("mvr", "MVR") var mvr: Double? = null,
    @JsonNames("mwk", "MWK") var mwk: Double? = null,
    @JsonNames("mxn", "MXN") var mxn: Double? = null,
    @JsonNames("myr", "MYR") var myr: Double? = null,
    @JsonNames("mzn", "MZN") var mzn: Double? = null,
    @JsonNames("nad", "NAD") var nad: Double? = null,
    @JsonNames("ngn", "NGN") var ngn: Double? = null,
    @JsonNames("nio", "NIO") var nio: Double? = null,
    @JsonNames("nok", "NOK") var nok: Double? = null,
    @JsonNames("npr", "NPR") var npr: Double? = null,
    @JsonNames("nzd", "NZD") var nzd: Double? = null,
    @JsonNames("omr", "OMR") var omr: Double? = null,
    @JsonNames("pab", "PAB") var pab: Double? = null,
    @JsonNames("pen", "PEN") var pen: Double? = null,
    @JsonNames("pgk", "PGK") var pgk: Double? = null,
    @JsonNames("php", "PHP") var php: Double? = null,
    @JsonNames("pkr", "PKR") var pkr: Double? = null,
    @JsonNames("pln", "PLN") var pln: Double? = null,
    @JsonNames("pyg", "PYG") var pyg: Double? = null,
    @JsonNames("qar", "QAR") var qar: Double? = null,
    @JsonNames("ron", "RON") var ron: Double? = null,
    @JsonNames("rsd", "RSD") var rsd: Double? = null,
    @JsonNames("rub", "RUB") var rub: Double? = null,
    @JsonNames("rwf", "RWF") var rwf: Double? = null,
    @JsonNames("sar", "SAR") var sar: Double? = null,
    @JsonNames("sbd", "SBD") var sbd: Double? = null,
    @JsonNames("scr", "SCR") var scr: Double? = null,
    @JsonNames("sdg", "SDG") var sdg: Double? = null,
    @JsonNames("sek", "SEK") var sek: Double? = null,
    @JsonNames("sgd", "SGD") var sgd: Double? = null,
    @JsonNames("shp", "SHP") var shp: Double? = null,
    @JsonNames("sll", "SLL") var sll: Double? = null,
    @JsonNames("sos", "SOS") var sos: Double? = null,
    @JsonNames("srd", "SRD") var srd: Double? = null,
    @JsonNames("ssp", "SSP") var ssp: Double? = null,
    @JsonNames("std", "STD") var std: Double? = null,
    @JsonNames("stn", "STN") var stn: Double? = null,
    @JsonNames("svc", "SVC") var svc: Double? = null,
    @JsonNames("syp", "SYP") var syp: Double? = null,
    @JsonNames("szl", "SZL") var szl: Double? = null,
    @JsonNames("thb", "THB") var thb: Double? = null,
    @JsonNames("tjs", "TJS") var tjs: Double? = null,
    @JsonNames("tmt", "TMT") var tmt: Double? = null,
    @JsonNames("tnd", "TND") var tnd: Double? = null,
    @JsonNames("top", "TOP") var top: Double? = null,
    @JsonNames("try", "TRY") var `try`: Double? = null,
    @JsonNames("ttd", "TTD") var ttd: Double? = null,
    @JsonNames("twd", "TWD") var twd: Double? = null,
    @JsonNames("tzs", "TZS") var tzs: Double? = null,
    @JsonNames("uah", "UAH") var uah: Double? = null,
    @JsonNames("ugx", "UGX") var ugx: Double? = null,
    @JsonNames("usd", "USD") var usd: Double? = null,
    @JsonNames("uyu", "UYU") var uyu: Double? = null,
    @JsonNames("uzs", "UZS") var uzs: Double? = null,
    @JsonNames("ves", "VES") var ves: Double? = null,
    @JsonNames("vnd", "VND") var vnd: Double? = null,
    @JsonNames("vuv", "VUV") var vuv: Double? = null,
    @JsonNames("wst", "WST") var wst: Double? = null,
    @JsonNames("xaf", "XAF") var xaf: Double? = null,
    @JsonNames("xag", "XAG") var xag: Double? = null,
    @JsonNames("xau", "XAU") var xau: Double? = null,
    @JsonNames("xcd", "XCD") var xcd: Double? = null,
    @JsonNames("xdr", "XDR") var xdr: Double? = null,
    @JsonNames("xof", "XOF") var xof: Double? = null,
    @JsonNames("xpd", "XPD") var xpd: Double? = null,
    @JsonNames("xpf", "XPF") var xpf: Double? = null,
    @JsonNames("xpt", "XPT") var xpt: Double? = null,
    @JsonNames("yer", "YER") var yer: Double? = null,
    @JsonNames("zar", "ZAR") var zar: Double? = null,
    @JsonNames("zmw", "ZMW") var zmw: Double? = null,
    @JsonNames("zwl", "ZWL") var zwl: Double? = null
)
