from setuptools import setup, find_packages

VERSION = '0.0.1' 
DESCRIPTION = 'gnani_asr_grpc_api'
LONG_DESCRIPTION = 'This is a grpc api for transcription of audio to text'

# Setting up
setup(
       # the name must match the folder name 'verysimplemodule'
        name="gnani-asr-grpc-api", 
        version=VERSION,
        author="gnani.ai",
        author_email="<api.service@gnani.ai>",
        description=DESCRIPTION,
        long_description=LONG_DESCRIPTION,
        packages=find_packages(),
        # package_data={'': ['hindi.wav']},
        package_data={'gnani_grpc_client.audio': ['*.wav', '*.amr'],
                'gnani_grpc_client.proto':['*.sh','*.proto'],
                'gnani_grpc_client':['*.pem']},
        include_package_data=True,
        install_requires=['grpcio', 'grpcio_tools'], 
        keywords=['python', 'automatic_text_to_speech'],
        classifiers= [
            "Development Status :: 3 - Alpha",
            "Intended Audience :: Education",
            "Programming Language :: Python :: 2",
            "Programming Language :: Python :: 3",
            "Operating System :: MacOS :: MacOS X",
            "Operating System :: Microsoft :: Windows",
        ]
)
