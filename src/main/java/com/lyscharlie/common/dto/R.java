/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lyscharlie.common.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 统一API响应结果封装
 *
 * @author Chill
 */
@Getter
@Setter
@ToString
@ApiModel(value = "返回信息")
@NoArgsConstructor
public class R<T> implements Serializable {

	private static final long serialVersionUID = -6982140165491792194L;

	@ApiModelProperty(value = "状态码", example = "200")
	private int code;

	@ApiModelProperty(value = "是否成功", example = "true")
	private boolean success;

	@ApiModelProperty(value = "承载数据", example = "我是对象")
	private T data;

	@ApiModelProperty(value = "返回消息", example = "操作成功")
	private String msg;

}
