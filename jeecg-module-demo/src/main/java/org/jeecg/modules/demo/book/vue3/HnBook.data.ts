import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '创建日期',
    align:"center",
    dataIndex: 'createTime'
   },
   {
    title: '更新日期',
    align:"center",
    dataIndex: 'updateTime'
   },
   {
    title: '标题',
    align:"center",
    dataIndex: 'title'
   },
   {
    title: '作者',
    align:"center",
    dataIndex: 'author'
   },
   {
    title: '出版年份',
    align:"center",
    dataIndex: 'publishtime',
    customRender:({text}) =>{
      return !text?"":(text.length>10?text.substr(0,10):text)
    },
   },
   {
    title: 'SBN',
    align:"center",
    dataIndex: 'sbn'
   },
   {
    title: '书名',
    align:"center",
    dataIndex: 'bookname'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '标题',
    field: 'title',
    component: 'Input',
  },
  {
    label: '作者',
    field: 'author',
    component: 'Input',
  },
  {
    label: '出版年份',
    field: 'publishtime',
    component: 'DatePicker',
  },
  {
    label: 'SBN',
    field: 'sbn',
    component: 'Input',
  },
  {
    label: '书名',
    field: 'bookname',
    component: 'Input',
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  createTime: {title: '创建日期',order: 0,view: 'datetime', type: 'string',},
  updateTime: {title: '更新日期',order: 1,view: 'datetime', type: 'string',},
  title: {title: '标题',order: 2,view: 'text', type: 'string',},
  author: {title: '作者',order: 3,view: 'text', type: 'string',},
  publishtime: {title: '出版年份',order: 4,view: 'date', type: 'string',},
  sbn: {title: 'SBN',order: 5,view: 'text', type: 'string',},
  bookname: {title: '书名',order: 6,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}