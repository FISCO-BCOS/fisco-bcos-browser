import Axios from 'axios'
import qs from 'qs'

let axiosIns =  Axios.create({
    timeout: 30*1000
});
// axiosIns.defaults.baseURL = 'http://127.0.0.1:8081';
axiosIns.defaults.headers.post['X-Requested-With'] = 'XMLHttpRequest';
axiosIns.defaults.headers.get['X-Requested-With'] = 'XMLHttpRequest';
axiosIns.defaults.responseType = 'json';
// axiosIns.defaults.baseURL = 'http:127.0.0.1:8080'
axiosIns.defaults.validateStatus = function () {
    return true
};
export default {
    axiosIns
}

// export function http(options) {
//         return new Promise((resolve,reject) => {
//             axiosIns(options).then(response => {
//                 resolve(response)
//             })
//                 .catch(error => {
//                     reject(error)
//                 })
//         })
// };
/**post请求方法
 * @param options
 * @return {Promise}
 */
export function post(options) {
    return new Promise((resolve, reject) => {
        axiosIns(options).then(response => {
            resolve(response)
        })
            .catch(error => {
                reject(error)
            })
    })
};
/**get请求方法
 * @param options
 * @return {Promise}
 */
export function get(options) {
    return new Promise((resolve, reject) => {
        axiosIns(options).then(response => {
            resolve(response)
        })
            .catch(error => {
                reject(error)
            })
    })
};

/**patch请求方法
 * @param options
 * @return {Promise}
 */
export function patch(options) {
    return new Promise((resolve, reject) => {
        axiosIns(options).then(response => {
            resolve(response)
        })
            .catch(error => {
                reject(error)
            })
    })
};
/**put请求方法
 * @param options
 * @return {Promise}
 */
export function put(options) {
    return new Promise((resolve, reject) => {
        axiosIns(options).then(response => {
            resolve(response)
        })
            .catch(error => {
                reject(error)
            })
    })
};
/**delete请求方法
 * @param options
 * @return {Promise}
 */
export function deleted(options) {
    return new Promise((resolve, reject) => {
        axiosIns(options).then(response => {
            resolve(response)
        })
            .catch(error => {
                reject(error)
            })
    })
};
