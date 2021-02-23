package me.ghost.data;

import me.ghost.Game;
import org.jsfml.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class TileLoader {

    public static final ExecutorService THREADS = Executors.newCachedThreadPool();

    private final Texture[] tileTexture = new Texture[3420];
    private final AtomicBoolean loaded = new AtomicBoolean(false);
    private final List<Integer> collidableTiles = new ArrayList<>(Arrays.asList(22, 23, 24, 25, 26, 27, 60, 61, 62, 63, 64, 65, 98, 99, 100, 101, 102, 103, 136, 137, 138, 139, 140, 141, 174, 175, 176, 177, 178, 179, 212, 213, 214, 215, 216, 217, 248, 249, 250, 251, 286, 287, 288, 289, 298, 299, 300, 301, 302, 303, 324, 325, 326, 327, 336, 337, 338, 339, 340, 341, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 362, 363, 364, 365, 374, 375, 376, 377, 378, 379, 387, 388, 389, 390, 391, 392, 393, 394, 395, 396, 400, 401, 402, 403, 412, 413, 414, 415, 416, 417, 425, 426, 427, 428, 429, 430, 431, 432, 433, 434, 438, 439, 440, 441, 450, 451, 452, 453, 454, 455, 463, 464, 465, 466, 467, 468, 469, 470, 471, 472, 476, 477, 478, 479, 488, 489, 490, 491, 492, 493, 514, 515, 516, 517, 526, 527, 528, 529, 530, 531, 532, 533, 534, 535, 536, 537, 538, 539, 540, 541, 542, 543, 570, 571, 572, 573, 574, 575, 576, 577, 578, 579, 580, 581, 608, 609, 612, 613, 614, 615, 616, 617, 618, 619, 620, 621, 622, 623, 646, 647, 650, 651, 652, 653, 656, 657, 658, 659, 660, 661, 684, 685, 688, 689, 690, 691, 694, 695, 696, 697, 698, 699, 722, 723, 724, 725, 726, 727, 728, 729, 730, 731, 732, 733, 734, 735, 736, 737, 760, 761, 762, 763, 764, 765, 766, 767, 768, 769, 770, 771, 798, 799, 800, 801, 802, 803, 804, 805, 806, 807, 808, 809, 916, 917, 918, 919, 920, 921, 926, 927, 954, 955, 956, 957, 958, 959, 964, 965, 968, 969, 970, 971, 972, 973, 974, 975, 992, 993, 1002, 1003, 1006, 1007, 1008, 1009, 1010, 1011, 1012, 1013, 1022, 1023, 1024, 1025, 1030, 1031, 1040, 1041, 1044, 1045, 1046, 1047, 1048, 1049, 1050, 1051, 1060, 1061, 1062, 1063, 1070, 1071, 1078, 1079, 1080, 1081, 1082, 1083, 1084, 1085, 1086, 1087, 1088, 1089, 1098, 1099, 1100, 1101, 1108, 1109, 1116, 1117, 1118, 1119, 1120, 1121, 1122, 1123, 1124, 1125, 1126, 1127, 1136, 1137, 1138, 1139, 1140, 1141, 1144, 1145, 1146, 1147, 1148, 1149, 1150, 1151, 1152, 1153, 1156, 1157, 1178, 1179, 1182, 1183, 1184, 1185, 1186, 1187, 1188, 1189, 1190, 1191, 1192, 1193, 1194, 1195, 1224, 1225, 1228, 1229, 1230, 1231, 1232, 1233, 1254, 1255, 1256, 1257, 1262, 1263, 1266, 1267, 1268, 1269, 1270, 1271, 1292, 1293, 1294, 1295, 1304, 1305, 1306, 1307, 1308, 1309, 1330, 1331, 1332, 1333, 1344, 1345, 1346, 1347, 1386, 1387, 1388, 1389, 1390, 1391, 1392, 1393, 1394, 1395, 1396, 1397, 1398, 1399, 1400, 1401, 1402, 1403, 1404, 1405, 1424, 1425, 1426, 1427, 1428, 1429, 1430, 1431, 1432, 1433, 1434, 1435, 1436, 1437, 1438, 1439, 1440, 1441, 1442, 1443, 1456, 1457, 1458, 1459, 1462, 1463, 1464, 1465, 1466, 1467, 1468, 1469, 1470, 1471, 1472, 1473, 1474, 1475, 1476, 1477, 1478, 1479, 1480, 1481, 1494, 1495, 1496, 1497, 1500, 1501, 1502, 1503, 1504, 1505, 1506, 1507, 1508, 1509, 1510, 1511, 1512, 1513, 1514, 1515, 1516, 1517, 1518, 1519, 1528, 1529, 1530, 1531, 1538, 1539, 1540, 1541, 1542, 1543, 1544, 1545, 1546, 1547, 1548, 1549, 1550, 1551, 1552, 1553, 1554, 1555, 1556, 1557, 1558, 1559, 1566, 1567, 1568, 1569, 1576, 1577, 1578, 1579, 1580, 1581, 1582, 1583, 1584, 1585, 1586, 1587, 1588, 1589, 1590, 1591, 1592, 1593, 1594, 1595, 1616, 1617, 1618, 1619, 1620, 1621, 1622, 1623, 1624, 1625, 1626, 1627, 1628, 1629, 1630, 1631, 1632, 1633, 1654, 1655, 1656, 1657, 1658, 1659, 1660, 1661, 1662, 1663, 1664, 1665, 1666, 1667, 1668, 1669, 1670, 1671, 1692, 1693, 1694, 1695, 1696, 1697, 1698, 1699, 1700, 1701, 1702, 1703, 1704, 1705, 1706, 1707, 1708, 1709, 1730, 1731, 1732, 1733, 1734, 1735, 1736, 1737, 1738, 1739, 1768, 1769, 1770, 1771, 1772, 1773, 1774, 1775, 1776, 1777, 1806, 1807, 1808, 1809, 1810, 1811, 1812, 1813, 1814, 1815, 1844, 1845, 1846, 1847, 1848, 1849, 1850, 1851, 1852, 1853, 1886, 1887, 2048, 2049, 2050, 2051, 2086, 2087, 2088, 2089, 2200, 2201, 2202, 2203, 2204, 2205, 2206, 2207, 2208, 2209, 2210, 2211, 2212, 2213, 2214, 2215, 2234, 2235, 2236, 2237, 2238, 2239, 2240, 2241, 2242, 2243, 2244, 2245, 2246, 2247, 2248, 2249, 2250, 2251, 2252, 2253, 2272, 2273, 2274, 2275, 2280, 2281, 2282, 2283, 2284, 2285, 2286, 2287, 2288, 2289, 2290, 2291, 2310, 2311, 2312, 2313, 2318, 2319, 2320, 2321, 2322, 2323, 2324, 2325, 2326, 2327, 2328, 2329, 2348, 2349, 2350, 2351, 2352, 2353, 2354, 2355, 2356, 2357, 2358, 2359, 2360, 2361, 2362, 2363, 2364, 2365, 2366, 2367, 2386, 2387, 2388, 2389, 2390, 2391, 2392, 2393, 2394, 2395, 2396, 2397, 2398, 2399, 2400, 2401, 2402, 2403, 2404, 2405, 2424, 2425, 2426, 2427, 2432, 2433, 2434, 2435, 2436, 2437, 2438, 2439, 2440, 2441, 2442, 2443, 2470, 2471, 2472, 2473, 2476, 2477, 2478, 2479, 2528, 2529, 2530, 2531, 2532, 2533, 2534, 2535, 2536, 2537, 2538, 2539, 2566, 2567, 2568, 2569, 2570, 2571, 2572, 2573, 2574, 2575, 2576, 2577, 2604, 2605, 2606, 2607, 2608, 2609, 2610, 2611, 2612, 2613, 2614, 2615, 2642, 2643, 2644, 2645, 2646, 2647, 2648, 2649, 2650, 2651, 2652, 2653, 2680, 2681, 2682, 2683, 2684, 2685, 2686, 2687, 2688, 2689, 2690, 2691, 2718, 2719, 2720, 2721, 2722, 2723, 2724, 2725, 2726, 2727, 2728, 2729, 2756, 2757, 2758, 2759, 2760, 2761, 2762, 2763, 2764, 2765, 2766, 2767, 2794, 2795, 2796, 2797, 2798, 2799, 2800, 2801, 2802, 2803, 2804, 2805, 2824, 2825, 2826, 2827, 2828, 2829, 2830, 2831, 2862, 2863, 2864, 2865, 2866, 2867, 2868, 2869, 2900, 2901, 2902, 2903, 2904, 2905, 2906, 2907, 2938, 2939, 2940, 2941, 2942, 2943, 2944, 2945, 2976, 2977, 2978, 2979, 2980, 2981, 2982, 3014, 3015, 3016, 3017, 3018, 3019, 3020, 3052, 3053, 3054, 3055, 3056, 3057, 3058, 3059, 3090, 3091, 3092, 3093, 3094, 3095, 3096, 3097, 3136, 3137, 3138, 3139, 3140, 3141, 3142, 3143, 3144, 3145, 3146, 3147, 3174, 3175, 3176, 3177, 3178, 3179, 3180, 3181, 3182, 3183, 3184, 3185, 3212, 3213, 3214, 3215, 3216, 3217, 3218, 3219, 3220, 3221, 3222, 3223, 3250, 3251, 3252, 3253, 3254, 3255, 3256, 3257, 3258, 3259, 3260, 3261, 3288, 3289, 3290, 3291, 3292, 3293, 3294, 3295, 3296, 3297, 3298, 3299, 3326, 3327, 3328, 3329, 3330, 3331, 3332, 3333, 3334, 3335, 3336, 3337, 3364, 3365, 3366, 3367, 3368, 3369, 3370, 3371, 3372, 3373, 3374, 3375, 3402, 3403, 3404, 3405, 3406, 3407, 3408, 3409, 3410, 3411, 3412, 3413));

    public TileLoader() {
        THREADS.submit(() -> {
            for (int i = 0; i < 3420; i++) {
                tileTexture[i] = loadTexture(getTilePath(i));
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // e.printStackTrace();
                System.out.println("Error in loading tiles");
            }

            this.loaded.set(true);
        });
    }

    private Texture loadTexture(String path){
        Texture texture = new Texture();
        try {
            texture.loadFromStream(Game.class.getClassLoader().getResourceAsStream(path));
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Error in loading texture");
        }
        return texture;
    }


    public String getTilePath(int index){
        return("tiles/tile"+index+".png32");
    }

    public Texture getTileTexture(int index){
        return tileTexture[index];
    }

    public boolean isLoaded() {
        return this.loaded.get();
    }

    public List<Integer> getCollidableTiles() {
        return collidableTiles;
    }
}
