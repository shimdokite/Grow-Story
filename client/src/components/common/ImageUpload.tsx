import React, { useRef, useState } from 'react';

import NoImage from './NoImage';
import CommonButton from './CommonButton';
import Preview from './Preview';

// 파일 크기가 2mb 이하인지 확인하는 함수
const checkFileSize = (file: File) => {
  if (file && file.type.startsWith('image/')) {
    if (file.size <= 2 * 1024 * 1024) {
      return true;
    }
  }
  return false;
};

function ImageUpload({ register, required, errors, clearErrors }: any) {
  // ref 분리
  const { ref } = register('image');

  const inputRef = useRef<HTMLInputElement | null>(null);
  const [imagePreview, setImagePreview] = useState<string | null>(null);

  // 이미지 미리보기 설정하는 함수
  const setPreview = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      const file = e.target.files[0];
      if (checkFileSize(file)) {
        setImagePreview(URL.createObjectURL(file));
        clearErrors('image');
        return;
      }
      alert('2mb 이하 이미지를 선택해주세요.');
    }
    return;
  };

  // 이미지 등록 버튼 클릭 시 input click
  const handleImageUploadClick = () => {
    if (inputRef.current) inputRef.current.click();
  };
  return (
    <div className="w-full flex flex-col items-center">
      {imagePreview ? <Preview src={imagePreview} /> : <NoImage />}
      <input
        className="hidden"
        {...register(
          'image',
          required
            ? {
                required: '이미지를 필수로 등록해야 합니다.',
              }
            : {},
        )}
        ref={(e) => {
          ref(e);
          inputRef.current = e;
        }}
        type="file"
        accept="image/*"
        onChange={(e) => setPreview(e)}
      />
      <label htmlFor="image">
        <CommonButton
          usage="button"
          size="sm"
          className="mt-3 mb-3 leading-4"
          handleImageUploadClick={handleImageUploadClick}>
          이미지 등록
        </CommonButton>
      </label>
      {Object.keys(errors).length ? (
        <p className="text-xs leading-3 font-normal text-red-50 mb-6">
          {errors.image.message}
        </p>
      ) : (
        <p className="text-xs leading-3 font-normal text-gray-70 mb-6">
          2mb 이하의 이미지만 등록이 가능합니다.
        </p>
      )}
    </div>
  );
}

export default ImageUpload;