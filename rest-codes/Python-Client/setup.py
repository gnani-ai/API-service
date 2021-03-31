from setuptools import setup, find_packages

VERSION = '0.0.2' 
DESCRIPTION = 'gnani_asr_rest_api'
LONG_DESCRIPTION = 'This is a rest api for transcription of audio to text'

# Setting up
setup(
       # the name must match the folder name 'verysimplemodule'
        name="gnani-asr-rest-api", 
        version=VERSION,
        author="gnani.ai",
        author_email="<api.service@gnani.ai>",
        description=DESCRIPTION,
        long_description=LONG_DESCRIPTION,
        packages=find_packages(),
        package_data={'gnani_python_client.audio': ['*.wav'], 'gnani_python_client': ['*.pem', '*.md','*.log']},
        include_package_data=True,
        install_requires=['requests'],
        keywords=['python', 'automatic text to speech'],
        classifiers= [
            "Development Status :: 3 - Alpha",
            "Intended Audience :: Education",
            "Programming Language :: Python :: 2",
            "Programming Language :: Python :: 3",
            "Operating System :: MacOS :: MacOS X",
            "Operating System :: Microsoft :: Windows",
        ]
)
